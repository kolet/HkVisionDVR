package com.example.andrei.cctv.hikvision;

import android.util.Log;

import com.example.andrei.cctv.DebugTools;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.RealPlayCallBack;
import com.hikvision.netsdk.SDKError;

import java.util.ArrayList;

public class HikVisionDvrManager {
    private static final String TAG = "HikVisionDvrManager";

    // Move this to DvrDeviceInfo
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    private static DvrDeviceInfo deviceInfo;

    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;

    private static HikVisionDvrManager manager = null;
    private static HCNetSDK hcNetSdk = new HCNetSDK();

    private int playTagID = -1;  // -1 = not playing, 0 = playing video
    private int userId = -1;

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

    //private DvrCameraSurfaceView playerView, playerView2;
    private ArrayList<DvrCamera> cameras = new ArrayList<>();
    private ArrayList<DvrCameraSurfaceView> cameraViews = new ArrayList<>();
    private int currentCamera = 0;

    public void addCamera(DvrCamera camera) {
        cameras.add(camera);
        cameraViews.add(camera.getCameraView());
   }
//
//    public void setPlayerView(DvrCameraSurfaceView playerView, int cameraId) {
//        cameraViews.set(cameraId, playerView);
//    }

//    public void setPlayerView2(DvrCameraSurfaceView playerView) {
//        this.playerView2 = playerView;
//    }

//    private SurfaceHolder surfaceHolder;
//
//    public void setSurfaceHolder(SurfaceHolder holder) {
//        this.surfaceHolder = holder;
//    }

    /**
     * Initialization of the whole network SDK, operations like memory pre-allocation.
     */
    public String init() {
//        if (playTagID >= 0) {
//            // currently playing
//            playTagID = -1;
//            playerView.stopPlaying();
//        }

        // used to initialize SDK.
        if (!hcNetSdk.NET_DVR_Init()) {
            Log.e(TAG, "Failed to initialize SDK！ Error: " + getErrorMessage());
            return getErrorMessage();
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
        return null;
    }

    /**
     * Log into device.
     * On successful registration returns a unique identifier user ID for other operations
     *
     * @return
     */
    public String login() {
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


    private void retryLogin(int numOfRetries) throws Exception {
        Log.e(TAG, "Trying to login again");

        int count = 0;
        while (count < numOfRetries) {
            Log.i(TAG, "In the " + (count + 1) + " re login");
            login();

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

    public String startStreaming(DvrCamera camera) {
        try {
//            if (playTagID >= 0) {
//                // Stop if was playing something
//                //stopPlaying();
//                Log.d(TAG, "Already playing?");
//                return "Already playing?";
//            }

            if (userId < 0) {
                // Make sure we are logged into the device
                retryLogin(5);
            }

            // Preview parameter configuration
            NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
            //clientInfo.lChannel = channel + dvr_deviceinfo.byStartChan;
            currentCamera = camera.getCameraId();
            clientInfo.lChannel = currentCamera;

            clientInfo.lLinkMode = camera.showFullScreen() ? 0 : 0x80000000;
//	    clientInfo.lLinkMode = 0x80000000;

            // A multicast address, multicast preview configuration needs
            clientInfo.sMultiCastIP = null;

            playTagID = hcNetSdk.NET_DVR_RealPlay_V30(userId, clientInfo, realplayCallback, true);

            if (playTagID < 0) {
                String errorMessage = getErrorMessage();
                Log.e(TAG, "Real time playback failure！" + errorMessage);
                return errorMessage;
            }

        } catch (Exception e) {
            Log.e(TAG, "Abnormal: " + e.toString());
            Log.e(TAG, getErrorMessage());
            e.printStackTrace();
            return getErrorMessage();
        }

        return null;
    }

    /**
     * Logout the user and free SDK resources
     */
    public void stopStreaming() {
        try {
            // Free the SDK
            if (playTagID != -1 && !hcNetSdk.NET_DVR_StopRealPlay(playTagID)) {
                Log.e(TAG, "Stop playing real-time failure！" + getErrorMessage());
            }

            if (!hcNetSdk.NET_DVR_Logout_V30(userId)) {
                Log.e(TAG, "Could not logout from the DVR！ Error: " + getErrorMessage());
            }

            userId = -1;
            playTagID = -1;

            hcNetSdk.NET_DVR_Cleanup();

            // Free the player view
            //playerView.stopPlaying();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopCamera(DvrCamera camera) {
        if (cameras == null || cameras.size() == 0) {
            return;
        }

        // Stop playing and free the camera view
        int cameraId = camera.getCameraId() - 1;
        DvrCamera dvrCamera = cameras.get(cameraId);

        if (dvrCamera != null) {
            dvrCamera.stopPlaying();
            cameras.remove(cameraId);
        }
    }

    private void saveDeviceParameters(NET_DVR_DEVICEINFO_V30 info) {
        if (info == null) return;

        DebugTools.dump(info);

        deviceInfo.channelNumber = info.byChanNum;
        deviceInfo.startChannel = info.byStartChan;

//        // Get device serial number
//        byte[] sbbyte = info.sSerialNumber;
//
//        String serialNumber = "";
//        for (int i = 0; i < sbbyte.length; i++) {
//            serialNumber += String.valueOf(sbbyte[i]);
//        }
//
//        deviceInfo.serialNumber = serialNumber;
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

//        int counter = 0;
//	        for ( byte byt : ipParaCfg.byAnalogChanEnable ) {
//	        	if ( CHANNEL_ENABLED == byt ) counter++;
//	        }

//        for (NET_DVR_IPCHANINFO entry : ipParaCfg.struIPChanInfo) {
//            if (1 == entry.byEnable) {
//                DebugTools.dump(entry);
//            }
//        }
//
//        DebugTools.dump(ipParaCfg);
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
            if (bufferSize == 0) {
                Log.d(TAG, "Play buffer is empty, skipping...");
                return;
            }

            if (cameras == null || cameras.size() == 0) {
                // No available cameras
                return;
            }

            DvrCamera cam = cameras.get(currentCamera - 1);

            if (cam == null) {
                return;
            }

            DvrCameraSurfaceView playerView = cam.getCameraView();

            try {
                switch (dataType) {
                    case HCNetSDK.NET_DVR_SYSHEAD:

                        if (!playerView.startPlaying(buffer, bufferSize)) {
                            Log.d(TAG, "Open player failed.");
                        }

                        break;

                    case HCNetSDK.NET_DVR_STREAMDATA:
                    case HCNetSDK.NET_DVR_STD_VIDEODATA:
                    case HCNetSDK.NET_DVR_STD_AUDIODATA:
                        playerView.streamData(buffer, bufferSize);
                        break;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Log.e(TAG, ex.getMessage());
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