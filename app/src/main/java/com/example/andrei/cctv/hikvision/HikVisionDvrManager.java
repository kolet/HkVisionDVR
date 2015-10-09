package com.example.andrei.cctv.hikvision;

import android.util.Log;
import android.view.SurfaceHolder;

import com.example.andrei.cctv.DebugTools;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPCHANINFO;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.RealPlayCallBack;
import com.hikvision.netsdk.SDKError;

import org.MediaPlayer.PlayM4.Player;

public class HikVisionDvrManager {
    private static final String TAG = "HikVisionDvrManager";

    // Move this to DvrDeviceInfo
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    private static DvrDeviceInfo deviceInfo;

    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;

    private static HikVisionDvrManager manager = null;
    private static HCNetSDK hcNetSdk = new HCNetSDK();
    private static Player player;

    private int playTagID = -1;  // -1 = not playing, 0 = playing video
    private int playPort = -1;
    private int userId = -1;
    public boolean isPlaying = false;

    //<editor-fold desc="Instance">

    private HikVisionDvrManager() {
        // allow only singletons
    }

    public static synchronized HikVisionDvrManager getInstance() {
        if (manager == null) {
            synchronized (HikVisionDvrManager.class) {
                manager = new HikVisionDvrManager();
                deviceInfo = new DvrDeviceInfo();
            }
        }

        return manager;
    }

    //</editor-fold>

    private SurfaceHolder surfaceHolder;

    public void setSurfaceHolder(SurfaceHolder holder) {
        this.surfaceHolder = holder;
    }

    /**
     * Initialization of the whole network SDK, operations like memory pre-allocation.
     */
    public  boolean initSDK() {
        if (playTagID >= 0) {
            // currently playing
            stopPlayer();
        }

        // used to initialize SDK.
        if (!hcNetSdk.NET_DVR_Init()) {
            Log.e(TAG, "Failed to initialize SDK！ Error: " + getErrorMessage());
            return false;
        }

        // This is optional, used to set the network connection timeout of SDK.
        // This is optional, used to set the network connection timeout of SDK.
        // User can set this value to their own needs.
        hcNetSdk.NET_DVR_SetConnectTime(Integer.MAX_VALUE);

        // Most module functions of the SDK are achieved by the asynchronous mode, so we provide this
        // interface for receiving reception message of preview, alarm, playback, transparent channel
        // and voice talk process.
        // Clients can set this callback function after initializing SDK, receive process exception
        // message of each module in application layer.
        hcNetSdk.NET_DVR_SetExceptionCallBack(exceptionCallback);
        return true;
    }

    public synchronized boolean initPlayer() {
        // Get player port
        player = Player.getInstance();
        playPort = player.getPort();

        if (playPort == -1) {
            Log.e(TAG, "Failed to get the MediaPlayer play port！");
            return false;
        }

        return true;
    }

    /**
     * Log into device.
     * On successful registration returns a unique identifier user ID for other operations
     *
     * @return
     */
    public String loginDevice() {
        NET_DVR_DEVICEINFO_V30 dvrInfo = new NET_DVR_DEVICEINFO_V30();

        // TODO: Server and Login details must be stored somewhere
        userId = hcNetSdk.NET_DVR_Login_V30(DVR_IP, DVR_PORT, "test", "a123456789", dvrInfo);

        if (userId < 0) {
            String errorMessage = getErrorMessage();
            Log.e(TAG, "LoginDevice() - " + errorMessage);
            return errorMessage;
        }

        saveDeviceParameters(dvrInfo);
        return null;
    }

    /**
     * Logout the user and free SDK resources
     */
    public void logoutDevice() {
        if (hcNetSdk.NET_DVR_Logout_V30(userId)) {
            userId = -1;
        } else {
            userId = 0;
            Log.e(TAG, "Could not logout from the DVR！ Error: " + getErrorMessage());
        }

        // Free the SDK
        hcNetSdk.NET_DVR_Cleanup();
    }

    private void loginDeviceRetry(int numOfRetries) throws Exception {
        Log.e(TAG, "Trying to login again");

        int count = 0;
        while (count < numOfRetries) {
            Log.i(TAG, "In the " + (count + 1) + " re login");
            loginDevice();

            if (userId < 0) {
                count++;
                Thread.sleep(200);
            } else {
                Log.i(TAG, "Article " + (count + 1) + " login successful");
                break;
            }
        }

        // Still could not login
        if (userId < 0) {
            Log.e(TAG, "Trying to sign " + count + " times - all failed！");
            return;
        }
    }

    public boolean startStreaming() {
        try {
            if (playTagID >= 0) {
                // Stop if was playing something
                //stopPlayer();
                Log.d(TAG, "Now playing?");
                return false;
            }

            if (userId < 0) {
                // Make sure we are logged into the device
                loginDeviceRetry(5);
            }

            // Preview parameter configuration
            NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
            //clientInfo.lChannel = channel + dvr_deviceinfo.byStartChan;
            clientInfo.lChannel = 1;
            clientInfo.lLinkMode = 0;
//	    clientInfo.lLinkMode = 0x80000000;

            // A multicast address, multicast preview configuration needs
            clientInfo.sMultiCastIP = null;

            playTagID = hcNetSdk.NET_DVR_RealPlay_V30(userId, clientInfo, realplayCallback, true);

            if (playTagID < 0) {
                Log.e(TAG, "Real time playback failure！" + getErrorMessage());
                return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Abnormal: " + e.toString());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean stopStreaming() {
        if (playTagID < 0) {
            Log.d(TAG, "Player has already stopped");
            return false;
        }

        isPlaying = false;

        // Stop the broadcast networks
        if (!hcNetSdk.NET_DVR_StopRealPlay(playTagID)) {
            Log.e(TAG, "Stop playing real-time failure！" + getErrorMessage());
            return false;
        }

        return true;
    }

    public boolean startPlayer(byte[] buffer, int bufferSize) {
        if (!player.setStreamOpenMode(playPort, Player.STREAM_REALTIME)) {
            Log.d(TAG, "The player set stream mode failed!");
            return false;
        }

        // Open the video stream
        if (!player.openStream(playPort, buffer, bufferSize, BUFFER_POOL_SIZE)) {
            Log.d(TAG, "Failed to open video stream using Player.openStream()");
            player.freePort(playPort);
            playPort = -1;
            return false;
        }

        // Set the video stream
        //player.setStreamOpenMode(playPort, Player.STREAM_REALTIME);

        // Set the playback buffer maximum buffer frames
//        if (!player.setDisplayBuf(playPort, 10)) { // Frame rate, is not set to the default 15
//            Log.e(TAG, "Set the playback buffer maximum buffer frames failed！");
//            return false;
//        }

        if (!player.play(playPort, this.surfaceHolder.getSurface())) {
            // Set the video flow failure
            player.closeStream(playPort);
            player.freePort(playPort);
            playPort = -1;

            return false;
        }

        isPlaying = true;
        return true;
    }

    public void stopPlayer() {
        if (player == null)
            return;

        if (!player.stop(playPort)) {
            Log.e(TAG, "Stop the play failed！");
        }

        // Close the video stream
        if (!player.closeStream(playPort)) {
            Log.e(TAG, "Close the video flow failure！");
        }

        // Release play port
        if (!player.freePort(playPort)) {
            Log.e(TAG, "Release port failed to play！");
        }

        playPort = -1;
        playTagID = -1;
        isPlaying = false;
    }

    private void saveDeviceParameters(NET_DVR_DEVICEINFO_V30 info) {
        if (info == null) return;

        DebugTools.dump(info);

        deviceInfo.channelNumber = info.byChanNum;
        deviceInfo.startChannel = info.byStartChan;

        // Get device serial number
        byte[] sbbyte = info.sSerialNumber;

        String serialNumber = "";
        for (int i = 0; i < sbbyte.length; i++) {
            serialNumber += String.valueOf(sbbyte[i]);
        }

        deviceInfo.serialNumber = serialNumber;
    }

    public void dumpUsefulInfo() {
        if (hcNetSdk == null) return;

        // Structure of IP device resource and IP channel resource configuration.
        NET_DVR_IPPARACFG_V40 ipParaCfg = new NET_DVR_IPPARACFG_V40();

        // UserId, Command, ChannelNo., Out
        hcNetSdk.NET_DVR_GetDVRConfig(userId, HCNetSDK.NET_DVR_GET_IPPARACFG_V40, 0, ipParaCfg);
        Log.d(TAG, String.format("NET_DVR_IPPARACFG_V40{ dwAChanNum : %s, dwDChanNum : %s, dwGroupNum : %s, dwStartDChan : %s }",
                ipParaCfg.dwAChanNum, ipParaCfg.dwDChanNum,
                ipParaCfg.dwGroupNum, ipParaCfg.dwStartDChan));

        int counter = 0;
//	        for ( byte byt : ipParaCfg.byAnalogChanEnable ) {
//	        	if ( CHANNEL_ENABLED == byt ) counter++;
//	        }

        for (NET_DVR_IPCHANINFO entry : ipParaCfg.struIPChanInfo) {
            if (1 == entry.byEnable) {
                DebugTools.dump(entry);
            }
        }

        DebugTools.dump(ipParaCfg);
        Log.i(TAG, getErrorMessage());

        //	        for ( NET_DVR_IPDEVINFO_V31 entry : ipParaCfg.struIPDevInfo ) {
//
//	        	if ( 1 == entry.byEnable ) {
//	        		// byProType
//	        		// Protocol type: 0- private (default), 1- Panasonic, 2- sony, get more NET_DVR_GetIPCProtoList。
//
//	        		//DebugTools.dump( entry );
//
//	        		System.out.println( "{" );
//		        	System.out.println( "  byDomain -> " + new String( Utility.getValidByte( entry.byDomain ) ) );
//		        	System.out.println( "  struIP.sIpV4 -> " + new String( Utility.getValidByte( entry.struIP.sIpV4 ) ) );
//		        	System.out.println( "  sUserName -> " + new String( Utility.getValidByte( entry.sUserName ) ) );
//		        	System.out.println( "  sPassword -> " + new String( Utility.getValidByte( entry.sPassword ) ) );
//		        	System.out.println( "  byProType -> " + entry.byProType );
//		        	System.out.println( "  wDVRPort -> " + entry.wDVRPort );
//		        	System.out.println( "}" );
//	        	}
//	        }
    }

    private ExceptionCallBack exceptionCallback = new ExceptionCallBack() {
        @Override
        public void fExceptionCallBack(int code, int userId, int handle) {
            String message = String.format("ExceptionCallBack::fExceptionCallBack( 0x%h, %s, %s )", code, userId, handle);
            Log.e(TAG, message);
        }
    };

    //Capture the callback function
//    private PlayerDisplayCB displayCB = new PlayerDisplayCB() {
//        @Override
//        public void onDisplay(int arg0, ByteBuffer arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
//            if (null != context) {
//                context.sendBroadcast(new Intent(ACTION_START_RENDERING));
//            } else {
//                Log.e(TAG, "The Context is empty! No setContext(Context context)?");
//            }
//            Log.d(TAG, "The start screen rendering");
//            if (Player.getInstance().setDisplayCB(playPort, null)) {
//                Log.i(TAG, "Remove display successfully！");
//            } else {
//                Log.e(TAG, "Remove display callback failure！");
//            }
//        }
//    };

    private RealPlayCallBack realplayCallback = new RealPlayCallBack() {

        /**
         * Video stream decoding.
         *
         * @param handle     current preview handler
         * @param dataType   The iDataType data type
         * @param buffer     PDataBuffer store data buffer pointer
         * @param bufferSize IDataSize buffer size
         */
        @Override
        public void fRealDataCallBack(int handle, int dataType, byte[] buffer, int bufferSize) {
            if (playPort == -1) {
                Log.d(TAG, "Play port is not ready!");
                return;
            }

            if (bufferSize == 0) {
                Log.d(TAG, "Play buffer is empty, skipping...");
                return;
            }

            try {
                switch (dataType) {
                    case HCNetSDK.NET_DVR_SYSHEAD:
                        if (!startPlayer(buffer, bufferSize)) {
                            Log.d(TAG, "Open player failed.");
                        }

                        break;

                    case HCNetSDK.NET_DVR_STREAMDATA:
                    case HCNetSDK.NET_DVR_STD_VIDEODATA:
                    case HCNetSDK.NET_DVR_STD_AUDIODATA:
                        if (isPlaying && player.inputData(playPort, buffer, bufferSize)) {
                            isPlaying = true;
                        } else {
                            isPlaying = false;
                            Log.d(TAG, "Playing failed: " + getErrorMessage());
                        }

//                            for (i = 0; i < 400; i++) {
//                                if (player.inputData(playPort, buffer, bufferSize)) {
//                                    //Log.d(TAG, "Played successfully.");
//                                    break;
//                                }
//
//                                if (i == 400) {
//                                    Log.d(TAG, "Playing failed.");
//                                    //Thread.sleep(10);
//                                    System.out.println(getErrorMessage());
//                                }
//                            }
                        break;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    };

    private String getErrorMessage() {
        int errorCode = hcNetSdk.NET_DVR_GetLastError();

        if (errorCode == 0) return "";

        Log.e(TAG, "Error: " + errorCode);

        switch (errorCode) {
            case SDKError.NET_DVR_NETWORK_FAIL_CONNECT:
                return "Connection Failed.\n\nThe DVR is off-line, or you are not connected to the same network.";
            case 17:
                return "NET_DVR_PARAMETER_ERROR: Parameter error. Input or output parameter in the SDK API is NULL.";
            case 400:
                return "InputData failed";
            default:
                return "UNKNOWN ERROR";
        }
    }

}