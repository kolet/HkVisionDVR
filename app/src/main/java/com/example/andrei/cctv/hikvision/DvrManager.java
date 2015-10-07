package com.example.andrei.cctv.hikvision;

import android.content.Context;
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

import org.MediaPlayer.PlayM4.Player;

public class DvrManager {
    private static final String TAG = "DvrManager";

    // Move this to DvrInfo
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    public static final int CHANNEL_TYPE_ANALOG = 1;
    public static final int CHANNEL_TYPE_DIGIT = 0;
    public static final int CHANNEL_TYPE_ZERO = 3;

    public static final byte CHANNEL_ENABLED = 1;
    public static final byte CHANNEL_DISABLED = 0;

    /**
     * Device information
     * byChanNum = analog channel number
     */
    private NET_DVR_DEVICEINFO_V30 dvrInfo = null;

    private Context context;
    //private Player player;

    private static HCNetSDK hcNetSdk = new HCNetSDK();

    /**
     * Playing tag: -1 is not playing, playing 0
     */
    private int playTagID = -1;

    private int userId = -1;

    //<editor-fold desc="Instance">

    private static DvrManager manager = null;

    private DvrManager() {
        // allow only singletons
    }

    public static synchronized DvrManager getInstance() {
        if (manager == null) {
            synchronized (DvrManager.class) {
                manager = new DvrManager();
            }
        }

        return manager;
    }

    //</editor-fold>

    private SurfaceHolder surfaceHolder;

    public void setSurfaceHolder(SurfaceHolder holder) {
        this.surfaceHolder = holder;
    }

    //<editor-fold desc="Player Port">

    private int playerPort = -1;

    public int getPlayerPort() {
        return playerPort;
    }

    public void setPlayerPort(int playerPort) {
        this.playerPort = playerPort;
    }

    //</editor-fold>

    /**
     * Initialization of the whole network SDK, operations like memory pre-allocation.
     */
    public synchronized void initSDK() {
        if (playTagID >= 0) {
            // currently playing
            stopPlayer();
        }

        if (hcNetSdk.NET_DVR_Init()) {
            Log.i(TAG, "Initialize the SDK success！");
        } else {
            Log.e(TAG, "Failed to initialize SDK！");
        }

        // optional, used to set the network connection timeout of SDK.
        // User can set this value to their own needs.
        // You will use the default value when you don't call this interface to set timeout.
        hcNetSdk.NET_DVR_SetConnectTime(Integer.MAX_VALUE);

        // Most module functions of the SDK are achieved by the asynchronous mode, so we provide
        // this interface for receiving reception message of preview, alarm, playback, transparent
        // channel and voice talk process.
        // Clients can set this callback function after initializing SDK, receive process exception
        // message of each module in application layer.
        hcNetSdk.NET_DVR_SetExceptionCallBack(exceptionCallback);
    }

    public synchronized boolean initPlayer() {
        // Get player port
        playerPort = Player.getInstance().getPort();

        if (playerPort == -1) {
            Log.e(TAG, "Get a player port failed！");
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
    public boolean loginDevice() {
        dvrInfo = new NET_DVR_DEVICEINFO_V30();
        userId = hcNetSdk.NET_DVR_Login_V30(DVR_IP, DVR_PORT, "test", "a123456789", dvrInfo);

        if (userId < 0) {
            Log.e(TAG, "Login failed！ Error: " + checkForErrors());
            return false;
        }

        return true;
    }

    public void logoutDevice() {
        if (hcNetSdk.NET_DVR_Logout_V30(userId)) {
            userId = -1;

        } else {
            userId = 0;
            Log.e(TAG, "Could not logout from the DVR！ Error: " + checkForErrors());
        }

        // Free the SDK
        hcNetSdk.NET_DVR_Cleanup();
    }

    private void loginDeviceRetry(int numOfRetries) throws Exception {
        if (userId < 0) {
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

            if (userId < 0) {
                Log.e(TAG, "Trying to sign " + count + " times - all failed！");
                return;
            }
        }
    }

    public void dumpUsefulInfo() {
        DebugTools.dump(dvrInfo);

        System.out.println("Below is the equipment information************************");
        System.out.println("Logged user userId=" + userId);
        System.out.println("The channel began=" + dvrInfo.byStartChan);
        System.out.println("The number of channels=" + dvrInfo.byChanNum);
        System.out.println("The device type=" + dvrInfo.byDVRType);
        System.out.println("The number of IP channels=" + dvrInfo.byIPChanNum);


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
            if (CHANNEL_ENABLED == entry.byEnable) {
                DebugTools.dump(entry);
            }
        }

        DebugTools.dump(ipParaCfg);
        Log.i(TAG, checkForErrors());

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

    public synchronized boolean initRealPlay() {
        try {
            // Need to be logged in
            loginDeviceRetry(10);

            if (playTagID >= 0) {
                // Stop if was playing something
                //stopPlayer();
                Log.d(TAG, "Now playing?");
                return false;
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
                Log.e(TAG, "Real time playback failure！" + checkForErrors());
                return false;
            }

        } catch (Exception e) {
            Log.e(TAG, "Abnormal: " + e.toString());
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean startPlayer(byte[] buffer, int bufferSize) {
        final Player player = Player.getInstance();

        if (player == null) {
            Log.e(TAG, "Player instance not initialised");
            return false;
        }

        if (!player.setStreamOpenMode(playerPort, Player.STREAM_REALTIME)) {
            Log.d(TAG, "The player set stream mode failed!");
            return false;
        }

        // Open the video stream
        if (!player.openStream(playerPort, buffer, bufferSize, BUFFER_SIZE)) {
            player.freePort(playerPort);
            playerPort = -1;

            return false;
        }

        // Set the video stream
        player.setStreamOpenMode(playerPort, Player.STREAM_REALTIME);

        // Set the playback buffer maximum buffer frames
        if (!player.setDisplayBuf(playerPort, 10)) { // Frame rate, is not set to the default 15
            Log.e(TAG, "Set the playback buffer maximum buffer frames failed！");
            return false;
        }

        if (!player.play(playerPort, this.surfaceHolder.getSurface())) {
            // Set the video flow failure
            player.closeStream(playerPort);
            player.freePort(playerPort);
            playerPort = -1;

            return false;
        }

        return true;
    }

    public synchronized void stopPlayer() {
        if (playTagID < 0) {
            Log.d(TAG, "Has stopped?");
            return;
        }

        userId = -1;

        // Stop the broadcast networks
        if (hcNetSdk.NET_DVR_StopRealPlay(playTagID)) {
            Log.i(TAG, "Stop playing in real time successfully！");
        } else {
            Log.e(TAG, "Stop playing real-time failure！" + checkForErrors());
            return;
        }

        // Stop local playback
        final Player player = Player.getInstance();

        if (player != null) {
            if (!player.stop(playerPort)) {
                Log.e(TAG, "Stop the play failed！");
                return;
            }

            // Close the video stream
            if (!player.closeStream(playerPort)) {
                Log.e(TAG, "Close the video flow failure！");
                return;
            }

            // Release play port
            if (!player.freePort(playerPort)) {
                Log.e(TAG, "Release port failed to play！");
                return;
            }

            playerPort = -1;
            playTagID = -1;
        }
    }

    private ExceptionCallBack exceptionCallback = new ExceptionCallBack() {
        @Override
        public void fExceptionCallBack(int code, int userId, int handle) {
            String message = String.format("ExceptionCallBack::fExceptionCallBack( 0x%h, %s, %s )", code, userId, handle);
            Log.d(TAG, message);
            System.out.println(message);
        }
    };

    //<editor-fold desc="Real-time playback callback">

    private RealPlayCallBack realplayCallback = new RealPlayCallBack() {

        /**
         * Video stream decoding.
         *
         * @param handle current preview handler
         * @param dataType The iDataType data type
         * @param buffer PDataBuffer store data buffer pointer
         * @param bufferSize IDataSize buffer size
         */
        @Override
        public void fRealDataCallBack(int handle, int dataType, byte[] buffer, int bufferSize) {
            int i = 0;

            switch (dataType) {
                // First data processing
                case HCNetSDK.NET_DVR_SYSHEAD:
                    if (-1 == (playerPort = Player.getInstance().getPort())) {
                        Log.d(TAG, "Can't get play port!");
                        return;
                    }

                    if (0 < bufferSize) {
                        if (startPlayer(buffer, bufferSize)) {
                            Log.d(TAG, "Open player successfully.");
                        } else {
                            Log.d(TAG, "Open player failed.");
                        }
                    }

                    break;

                case HCNetSDK.NET_DVR_STREAMDATA:
                case HCNetSDK.NET_DVR_STD_VIDEODATA:
                case HCNetSDK.NET_DVR_STD_AUDIODATA:
                    if (0 < bufferSize && -1 != playerPort) {
                        try {
                            for (i = 0; i < 400; i++) {
                                if (Player.getInstance().inputData(playerPort, buffer, bufferSize)) {
                                    Log.d(TAG, "Played successfully.");
                                    break;
                                }

                                Log.d(TAG, "Playing failed.");
                                //Thread.sleep(10);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                        if (i == 400) {
                            System.out.println("inputData failed");
                        }

//					if ( Player.getInstance().inputData( playPort, buffer, bufferSize ) ) {
//						System.out.println( "Played successfully." );
//					} else {
//						System.out.println( "Playing failed." );
//					}
                    }

//				if ( -1 != playPort ) {
//					// closing the player
//				}
//
//				if ( openPlayer( buffer, bufferSize ) ) {
//
//				}
            }

            //if ( -1 == playPort ) return;

            //Player.getInstance().inputData( playPort, buffer, bufferSize );
        }
    };

    //</editor-fold>

    private String checkForErrors() {
        int errorCode = hcNetSdk.NET_DVR_GetLastError();

        if (errorCode == 0) return "";

        Log.e(TAG, "Error: " + errorCode);

        switch (errorCode) {
            case 17:
                return "TODO";
            default:
                return "UNKNOWN ERROR";
        }
    }
}
