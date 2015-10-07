package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_IPCHANINFO;
import com.hikvision.netsdk.NET_DVR_IPPARACFG_V40;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView surface;

    private Player player;
    private int playPort = -1;
    /**
     * Playing tag: -1 is not playing, playing 0
     */
    private int playTagID = -1;

    private static final int BUFFER_SIZE = 1024 * 1024 * 4;

    private HCNetSDK hcNetSdk;
    int userId = -1;

    public static final int CHANNEL_TYPE_ANALOG = 1;
    public static final int CHANNEL_TYPE_DIGIT = 0;
    public static final int CHANNEL_TYPE_ZERO = 3;

    public static final byte CHANNEL_ENABLED = 1;
    public static final byte CHANNEL_DISABLED = 0;

    private static final String videoPath = "rtsp://test:q123456789@192.168.1.10:554/Streaming/Channels/102";

    // IP address or static domain name of the device, the count of the characters should not more than 128
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surface = (SurfaceView) findViewById(R.id.surface);
//		surface.getHolder().setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
        //  surface.getHolder().addCallback(this);
        surface.getHolder().addCallback(this);

        new InitializeSdkTask().execute();
    }

    private ExceptionCallBack exceptionCallback = new ExceptionCallBack() {
        @Override
        public void fExceptionCallBack(int code, int userId, int handle) {
            String message = String.format("ExceptionCallBack::fExceptionCallBack( 0x%h, %s, %s )", code, userId, handle);
            Log.d(TAG, message);
            System.out.println(message);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopPlayer();

        if (hcNetSdk != null) {
            // Logout from the device
            hcNetSdk.NET_DVR_Logout_V30(userId);

            // Free the SDK
            hcNetSdk.NET_DVR_Cleanup();
        }
    }

    public void catchErrorIfNecessary() {
        int code = hcNetSdk.NET_DVR_GetLastError();

        if (0 != code)
            System.out.println("Error: " + code);
    }

    //<editor-fold desc="Initialize SDK">

    private class InitializeSdkTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            final long begin = System.currentTimeMillis();

            hcNetSdk = new HCNetSDK();

            // Initialization of the whole network SDK, operations like memory pre-allocation.
            hcNetSdk.NET_DVR_Init();

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

            player = Player.getInstance();

            // Get play port
            playPort = player.getPort();
            catchErrorIfNecessary();

            // Log into device.
            // After registering sucessfully,the returned user ID as a unique identifier for other operations
            NET_DVR_DEVICEINFO_V30 dvrInfo = new NET_DVR_DEVICEINFO_V30();
            userId = hcNetSdk.NET_DVR_Login_V30(DVR_IP, DVR_PORT, "test", "q123456789", dvrInfo);

            if (userId < 0) {
                Log.d(TAG, "Login failed");
                catchErrorIfNecessary();
            }

            DebugTools.dump(dvrInfo);
            Log.d(TAG, "Attempting to login: userId " + userId);
            Log.d(TAG, String.format("DeviceInfo: The channel began=%s, number of IP channels==%s, number of channels=%s",
                    dvrInfo.byStartChan, dvrInfo.byIPChanNum, dvrInfo.byChanNum));

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
                }}

            DebugTools.dump(ipParaCfg);
            catchErrorIfNecessary();

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

            // Preview parameter configuration
            NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
            //clientInfo.lChannel = channel + dvr_deviceinfo.byStartChan;
            clientInfo.lChannel = 1;
            clientInfo.lLinkMode = 0;
//	        clientInfo.lLinkMode = 0x80000000;

            // A multicast address, multicast preview configuration needs
            clientInfo.sMultiCastIP = null;

            // UserId, ClientInfo, RealplayCallback, Blocked
            playTagID = hcNetSdk.NET_DVR_RealPlay_V30(userId, clientInfo, realplayCallback, true);
            catchErrorIfNecessary();

            if (playTagID >= 0) {
                // Stop if was playing something
                //stopPlayer();
            }

            return null;
        }
    }

    //</editor-fold>

    private boolean openPlayer(byte[] buffer, int bufferSize) {
        if (!player.setStreamOpenMode(playPort, Player.STREAM_REALTIME)) {
            Log.d(TAG, "The player set stream mode failed!");
            return false;
        }

        // Open the video stream
        if (!player.openStream(playPort, buffer, bufferSize, BUFFER_SIZE)) {
            player.freePort(playPort);
            playPort = -1;

            return false;
        }

        // Set the video stream
        player.setStreamOpenMode(playPort, Player.STREAM_REALTIME);

        // Set the playback buffer maximum buffer frames
        if (!player.setDisplayBuf(playPort, 10)) { // Frame rate, is not set to the default 15
            Log.e(TAG, "Set the playback buffer maximum buffer frames failed！");
            return false;
        }

        if (!player.play(playPort, surface.getHolder().getSurface())) {
            // Set the video flow failure
            player.closeStream(playPort);
            player.freePort(playPort);
            playPort = -1;

            return false;
        }

        return true;
    }

    private synchronized void stopPlayer() {
        if (playTagID <0) {
            Log.d(TAG, "Has stopped somehow?");
            return;
        }

        // Stop the broadcast networks
        hcNetSdk.NET_DVR_StopRealPlay(playTagID);
        catchErrorIfNecessary();

        // Stop local playback
        if (!player.stop(playPort)) {
            Log.e(TAG, "Stop the play failed！");
            return;
        }

        // Close the video stream
        if (!player.closeStream(playPort)) {
            Log.e(TAG, "Close the video flow failure！");
            return;
        }

        // Release play port
        if (!player.freePort(playPort)) {
            Log.e(TAG, "Release port failed to play！");
            return;
        }

        playPort = -1;
        playTagID = -1;
    }

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
                    if (-1 == (playPort = player.getPort())) {
                        Log.d(TAG, "Can't get play port!");
                        return;
                    }

                    if (0 < bufferSize) {
                        if (openPlayer(buffer, bufferSize)) {
                            Log.d(TAG, "Open player successfully.");
                        } else {
                            Log.d(TAG, "Open player failed.");
                        }
                    }

                    break;

                case HCNetSDK.NET_DVR_STREAMDATA:
                case HCNetSDK.NET_DVR_STD_VIDEODATA:
                case HCNetSDK.NET_DVR_STD_AUDIODATA:
                    if (0 < bufferSize && -1 != playPort) {
                        try {
                            for (i = 0; i < 400; i++) {
                                if (Player.getInstance().inputData(playPort, buffer, bufferSize)) {
                                    Log.d(TAG, "Played successfully.");
                                    break;
                                }

                                Log.d(TAG, "Playing failed.");
                                Thread.sleep(10);
                            }
                        } catch (Exception e) {

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

    //<editor-fold desc="SurfaceHolder.Callback">

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//		if ( -1 == playPort ) {
        System.out.println("surfaceCreated: ");

        if (holder.getSurface().isValid()) {
            if (!player.setVideoWindow(playPort, 0, holder.getSurface())) {
                System.out.println("player set video window failed!");
            }
        }
//
//		this.holder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("surfaceChanged: ");
        if (holder.getSurface().isValid()) {
            if (!Player.getInstance().setVideoWindow(playPort, 0, null)) {
                System.out.println("player release video window failed!");
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //</editor-fold>
}
