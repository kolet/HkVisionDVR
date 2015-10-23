package com.example.andrei.cctv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.RealPlayCallBack;

import org.MediaPlayer.PlayM4.Player;

public class MultiCameraPreviewActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "asdasdas";
    private static final int[] SURFACE_RES_IDS = {R.id.video_1_surfaceview, R.id.video_2_surfaceview};

    private static final Player player = Player.getInstance();
    private static final HCNetSDK hcNetSDK = new HCNetSDK();

    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;

    private int playPort = -1;
    private boolean isPlaying = false;
    private int userId = -1;

    private int playTagID = -1;

    private SurfaceView[] mSurfaceViews = new SurfaceView[SURFACE_RES_IDS.length];
    private SurfaceHolder[] mSurfaceHolders = new SurfaceHolder[SURFACE_RES_IDS.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_camera_preview);

        // create surface holders
        for (int i = 0; i < mSurfaceViews.length; i++) {
            mSurfaceViews[i] = (SurfaceView) findViewById(SURFACE_RES_IDS[i]);
            mSurfaceHolders[i] = mSurfaceViews[i].getHolder();
            mSurfaceHolders[i].addCallback(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDVR();
        startStreaming();
    }

    private void startStreaming() {

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
        stopStreaming();
    }

    private void stopStreaming() {
        hcNetSDK.NET_DVR_StopRealPlay(playTagID);
        hcNetSDK.NET_DVR_Logout_V30(userId);
        hcNetSDK.NET_DVR_Cleanup();

        userId = -1;
        playTagID = -1;
    }

    private void stopPlaying() {
        Player.getInstance().stop(this.playPort);
        Player.getInstance().closeStream(this.playPort);
        Player.getInstance().freePort(this.playPort);
        this.playPort = -1;
    }

    private void initDVR() {
        // initialise SDK
        hcNetSDK.NET_DVR_Init();

        // Set connection time
        hcNetSDK.NET_DVR_SetConnectTime(60000);

        // Login
        NET_DVR_DEVICEINFO_V30 dvrInfo = new NET_DVR_DEVICEINFO_V30();
        userId = hcNetSDK.NET_DVR_Login_V30("192.168.1.10", 8000, "test", "a123456789", dvrInfo);

        if (userId < 0) {
            System.out.println("Login error: " + hcNetSDK.NET_DVR_GetLastError());
            hcNetSDK.NET_DVR_Cleanup();
        }

        // set exception callback
        hcNetSDK.NET_DVR_SetExceptionCallBack(exceptionCallback);


        NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();

        // preview channel number
        clientInfo.lChannel = 1;

        clientInfo.lLinkMode = 0;   // main stream
//	    clientInfo.lLinkMode = 0x80000000;  // substream

        // A multicast address, multicast preview configuration needs
        clientInfo.sMultiCastIP = null;

        // start preview
        playTagID = hcNetSDK.NET_DVR_RealPlay_V30(userId, clientInfo, realplayCallback, true);

        if (playTagID < 0) {
            stopStreaming();
        }

    }

    private ExceptionCallBack exceptionCallback = new ExceptionCallBack() {
        @Override
        public void fExceptionCallBack(int code, int userId, int handle) {
            String message = String.format("ExceptionCallBack::fExceptionCallBack( 0x%h, %s, %s )", code, userId, handle);
            Log.e(TAG, message);
        }
    };

    private RealPlayCallBack realplayCallback = new RealPlayCallBack() {
        @Override
        public void fRealDataCallBack(int handle, int dataType, byte[] buffer, int bufferSize) {
            if (bufferSize == 0) {
                Log.d(TAG, "Play buffer is empty, skipping...");
                return;
            }

            try {
                switch (dataType) {
                    case HCNetSDK.NET_DVR_SYSHEAD:
                        // Get Unused port
                        playPort = player.getPort();

                        if (playPort < 0) {
                            break;
                        }

                        if (bufferSize > 0) {
                            // set real-time stream playing mode
                            if (!player.setStreamOpenMode(playPort, Player.STREAM_REALTIME)) {
                                System.out.println("Stream failyure");
                                break;
                            }
                            // Open the video stream
                            if (!player.openStream(playPort, buffer, bufferSize, BUFFER_POOL_SIZE)) {
                                Log.d(TAG, "Failed to open video stream using Player.openStream()");
                                Player.getInstance().freePort(playPort);
                                playPort = -1;
                                break;
                            }

                            // Start play
                            if (!player.play(playPort, mSurfaceHolders[0].getSurface())) {
                                Log.d(TAG, "Failed to play");
                                break;

                            }
                        }

                        break;

                    case HCNetSDK.NET_DVR_STREAMDATA:
                    case HCNetSDK.NET_DVR_STD_VIDEODATA:
                    case HCNetSDK.NET_DVR_STD_AUDIODATA:

                        if (bufferSize > 0 && playPort != -1) {
                            if (/*isPlaying && */player.inputData(playPort, buffer, bufferSize)) {
                                isPlaying = true;
                            } else {
                                isPlaying = false;
                                Log.d(TAG, "Playing failed: " /*+ getErrorMessage()*/);
                            }
                        }
                        break;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                Log.e(TAG, ex.getMessage());
            }
        }
    };

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "SurfaceHolder(" + indexOf(holder) + "): surfaceCreated called");

        int index = indexOf(holder);
        if (index == -1) return; // sanity check; should never happen

        try {
            if (!player.setVideoWindow(playPort, 0, mSurfaceHolders[0].getSurface())) {
                System.out.println("player set video window failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "SurfaceHolder(" + indexOf(holder) + "): surfaceChanged called");
//        if (!player.setVideoWindow(playPort, 0, null)) {
//            System.out.println("player release video window failed!");
//        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "SurfaceHolder(" + indexOf(holder) + "): surfaceDestroyed called");
    }

    private int indexOf(SurfaceHolder holder) {
        for (int i = 0; i < mSurfaceHolders.length; i++) {
            if (mSurfaceHolders[i] == holder) return i;
        }

        return -1;
    }
}
