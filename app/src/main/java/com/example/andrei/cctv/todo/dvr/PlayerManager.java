package com.example.andrei.cctv.todo.dvr;

import android.util.Log;
import android.view.SurfaceHolder;

import org.MediaPlayer.PlayM4.Player;

public class PlayerManager {
    private static final String TAG = PlayerManager.class.getSimpleName();

    /**
     * The size of the source buffer.
     */
    private static final int BUFFER_POOL_SIZE = 1024 * 1024 * 4;

    private static SurfaceHolder surfaceHolder;
    private static int playPort = -1;

    private PlayerManager() {}

    public static boolean initPlayer(/*SurfaceHolder holder*/) {
        final Player player = Player.getInstance();

        if (player == null) {
            Log.e(TAG, "Player instance not initialised");
            return false;
        }

        // Get player port
        playPort =  player.getPort();

        if (playPort == -1) {
            Log.e(TAG, "Get a player port failed！");
            return false;
        }

        if (!player.setStreamOpenMode(playPort, Player.STREAM_REALTIME)) {
            Log.d(TAG, "The player set stream mode failed!");
            return false;
        }

        //surfaceHolder = holder;

        return true;
    }

    public static boolean startPlayer(byte[] buffer, int bufferSize) {
        final Player player = PlayerManager.getPlayer();

        if (player == null) {
            Log.e(TAG, "Player instance not initialised");
            return false;
        }

        // Open the video stream
        if (!player.openStream(playPort, buffer, bufferSize, BUFFER_POOL_SIZE)) {
            Log.e(TAG, "Failed to open video stream");
            player.freePort(playPort);
            playPort = -1;
            return false;
        }

        // Set the video stream
        //player.setStreamOpenMode(playPort, Player.STREAM_REALTIME);

        // Set the playback buffer maximum buffer frames
//        if (!player.setDisplayBuf(playerPort, 10)) { // Frame rate, is not set to the default 15
//            Log.e(TAG, "Set the playback buffer maximum buffer frames failed！");
//            return false;
//        }

        if (surfaceHolder == null) {
            throw new IllegalArgumentException("SurfaceHolder needs to be set up before playing video");
        }

        if (!player.play(playPort, surfaceHolder.getSurface())) {
            Log.e(TAG, "Failed to play video");

            // Set the video flow failure
            player.closeStream(playPort);
            player.freePort(playPort);
            playPort = -1;
            return false;
        }

        return true;
    }

    /**
     * Stop Player and free resources
     */
    public static boolean stopPlayer() {
        final Player player = Player.getInstance();

        if (playPort == -1) return true;

        if (!player.stop(playPort)) {
            Log.e(TAG, "Stop the player failed！");
            return false;
        }

        // Close the video stream
        if (!player.closeStream(playPort)) {
            Log.e(TAG, "Close the video flow failure！");
            return false;
        }

        // Release play port
        if (!player.freePort(playPort)) {
            Log.e(TAG, "Release port failed to play！");
            return false;
        }

        playPort = -1;
        return true;
    }

    public static Player getPlayer() {
        return Player.getInstance();
    }

    public static int getPort() {
        return playPort;
    }

    public static SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }

    public static void setSurfaceHolder(SurfaceHolder holder) {
        surfaceHolder = holder;
    }
}
