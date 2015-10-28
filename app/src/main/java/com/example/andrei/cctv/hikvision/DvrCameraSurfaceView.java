package com.example.andrei.cctv.hikvision;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_CLIENTINFO;
import com.hikvision.netsdk.RealPlayCallBack;
import com.hikvision.netsdk.SDKError;

import org.MediaPlayer.PlayM4.Player;

public class DvrCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "DvrCameraSurfaceView";

    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;
    private static final HCNetSDK hcNetSdk = new HCNetSDK();
    private int playPort = -1;
    private int playTagID = -1;

    public DvrCameraSurfaceView(Context context) {
        super(context);

        if (!isInEditMode()) {
            init();
        }
    }

    public DvrCameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            init();
        }
    }

    public DvrCameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (!isInEditMode()) {
            init();
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    /**
     * Starts streaming video from the DVR.
     *
     * @param channelId  Camera Id
     * @param mainStream Whether to show higher-resolution Main stream or lower-resolution Substream.
     */
    public void startPlaying(int channelId, boolean mainStream) {
        playPort = Player.getInstance().getPort();

        // Preview parameter configuration
        NET_DVR_CLIENTINFO clientInfo = new NET_DVR_CLIENTINFO();
        clientInfo.lChannel = channelId;
        clientInfo.lLinkMode = mainStream ? 0 : 0x80000000;

        // A multicast address, multicast preview configuration needs
        clientInfo.sMultiCastIP = null;

        int userId = HikVisionDvrManager.getInstance().getUserId();

        playTagID = hcNetSdk.NET_DVR_RealPlay_V30(userId, clientInfo, realplayCallback, true);

        if (playTagID < 0) {
            String errorMessage = getErrorMessage();
            Log.e(TAG, "Real time playback failure！" + errorMessage);
        }
    }

    public void stopPlaying() {
        try {
            if (!Player.getInstance().stop(playPort)) {
                Log.e(TAG, "Stop the play failed！");
            }

            if (!Player.getInstance().closeStream(playPort)) {
                Log.e(TAG, "Close the video flow failure！");
            }

            if (!Player.getInstance().freePort(playPort)) {
                Log.e(TAG, "Release port failed to play！");
            }

            playPort = -1;
            playTagID = -1;

            destroyDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (playPort < 0) return;

        // There's a short delay between the start of the activity and the initialization
        // of the SurfaceHolder that backs the SurfaceView.  We don't want to try to
        // send a video openPlayer to the SurfaceView before it has initialized.
        if (!Player.getInstance().setVideoWindow(playPort, 0, this.getHolder().getSurface())) {
            System.out.println("player set video window failed!");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (playPort < 0) return;

        if (!Player.getInstance().setVideoWindow(playPort, 0, null)) {
            System.out.println("player release video window failed!");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (realplayCallback != null) {
            getHolder().removeCallback(this);
        }
//        this.getHolder().removeCallback(this);
//        this.getHolder().getSurface().release();
    }

    private RealPlayCallBack realplayCallback = new RealPlayCallBack() {
        /**
         * Video openPlayer decoding.
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

            if (playPort < 0) {
                Log.d(TAG, "Play port is not ready!");
                return;
            }

            try {
                switch (dataType) {
                    case HCNetSDK.NET_DVR_SYSHEAD:
                        // The data called back at the first time is system header
                        if (!Player.getInstance().setStreamOpenMode(playPort, Player.STREAM_REALTIME)) {
                            Log.d(TAG, "setStreamOpenMode() failed!");
                            break;
                        }

                        // Open the video openPlayer
                        if (!Player.getInstance().openStream(playPort, buffer, bufferSize, BUFFER_POOL_SIZE)) {
                            Log.d(TAG, "Failed to open video player using Player.openStream()");
                            Player.getInstance().freePort(playPort);
                            playPort = -1;
                            break;
                        }

                        if (!Player.getInstance().play(playPort, getHolder().getSurface())) {
                            //stopPlaying();
                            Log.d(TAG, "Failed to play");
                            break;
                        }

                        break;

                    case HCNetSDK.NET_DVR_STREAMDATA:
                    case HCNetSDK.NET_DVR_STD_VIDEODATA:
                    case HCNetSDK.NET_DVR_STD_AUDIODATA:
                        // Streaming data
                        if (!Player.getInstance().inputData(playPort, buffer, bufferSize)) {
                            Log.d(TAG, "Streaming data failed: " /*+ getErrorMessage()*/);
                        }
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
