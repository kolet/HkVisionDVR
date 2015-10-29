package com.example.andrei.cctv.hikvision;

import android.util.Log;

import com.example.andrei.cctv.utils.DebugTools;
import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.SDKError;

public class HikVisionDvrManager {
    private static final String TAG = "HikVisionDvrManager";

    // TODO: Move this to DvrDeviceInfo
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    private static DvrDeviceInfo deviceInfo;

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

    /**
     * Initialization of the whole network SDK, operations like memory pre-allocation.
     */
    public String init() {
        // initialize SDK
        if (!hcNetSdk.NET_DVR_Init()) {
            Log.e(TAG, "Failed to initialize SDK Error: " + getErrorMessage());
            return getErrorMessage();
        }

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
        String errorMessage = null;

        // TODO: Server and Login details must be stored somewhere
        userId = hcNetSdk.NET_DVR_Login_V30(DVR_IP, DVR_PORT, "test", "a123456789", dvrInfo);

        if (userId < 0) {
            try {
                Log.e(TAG, "Trying to login again");

                int count = 0;
                while (count < 10) {
                    userId = hcNetSdk.NET_DVR_Login_V30(DVR_IP, DVR_PORT, "test", "a123456789", dvrInfo);

                    if (userId >0) {
                        break;
                    }

                    count++;
                    Thread.sleep(500);
                }

                // Still could not login
                if (userId < 0) {
                    errorMessage = "Tried to sign into the DVR " + count + " times - all failed!";
                    Log.e(TAG, errorMessage);
                }

            } catch (Exception e) {
                e.printStackTrace();
                errorMessage = getErrorMessage();
            }
        } else {
            // we logged in successfully
            saveDeviceParameters(dvrInfo);
        }

        isInitialised = errorMessage == null;

        return errorMessage;
    }

    /**
     * Stop streaming data to the specific play port
     */
    public void logout(int playPort) {
        try {
            // Free the SDK
            hcNetSdk.NET_DVR_StopRealPlay(playPort);
            hcNetSdk.NET_DVR_Logout_V30(userId);

            userId = -1;

            hcNetSdk.NET_DVR_Cleanup();

            isInitialised = false;

        } catch (Exception e) {
            e.printStackTrace();
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


    private boolean isInitialised;

    public boolean isInitialised() {
        return isInitialised;
    }

    public synchronized void setInitialised(boolean isInitialised) {
        this.isInitialised = isInitialised;
    }

    public int getUserId() {
        return this.userId;
    }

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