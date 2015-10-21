package com.example.andrei.cctv;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.MediaPlayer.PlayM4.Player;

public class DvrCameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "DvrCameraSurfaceView";

    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;

    private SurfaceHolder surfaceHolder = null;
    private Player player = null;
    private int playPort = -1;
    public boolean isPlaying = false;

    public DvrCameraSurfaceView(Context context) {
        super(context);

        if (isInEditMode()) {
            init();
        }
    }

    private void init() {
        getHolder().addCallback(this);
    }

    public DvrCameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (isInEditMode()) {
            init();
        }
    }

    public DvrCameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            init();
        }
    }

    public void stop() {
        try {
            isPlaying = false;

            if (player != null) {
                if (!player.stop(playPort)) {
                    Log.e(TAG, "Stop the play failed！");
                }

                if (!player.closeStream(playPort)) {
                    Log.e(TAG, "Close the video flow failure！");
                }

                if (!player.freePort(playPort)) {
                    Log.e(TAG, "Release port failed to play！");
                }

                playPort = -1;

                destroyDrawingCache();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean start(byte[] buffer, int bufferSize) {
        player = Player.getInstance();
        playPort = player.getPort();

        if (playPort == -1) {
            Log.d(TAG, "Play port is not ready!");
            return false;
        }

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

        if (!player.play(playPort, this.surfaceHolder.getSurface())) {
            // Set the video flow failure
//            player.closeStream(playPort);
//            player.freePort(playPort);
//            playPort = -1;

            stop();

            return false;
        }

        isPlaying = true;
        return true;
    }

    public void streamData(byte[] buffer, int bufferSize) {
        if (isPlaying && player.inputData(playPort, buffer, bufferSize)) {
            isPlaying = true;
        } else {
            isPlaying = false;
            Log.d(TAG, "Playing failed: " /*+ getErrorMessage()*/);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        holder = this.getHolder();

        if (!holder.getSurface().isValid()) return;

        if (!player.setVideoWindow(player.getPort(), 0, holder.getSurface())) {
            System.out.println("player set video window failed!");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (!holder.getSurface().isValid()) return;

        if (!player.setVideoWindow(player.getPort(), 0, null)) {
            System.out.println("player release video window failed!");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
