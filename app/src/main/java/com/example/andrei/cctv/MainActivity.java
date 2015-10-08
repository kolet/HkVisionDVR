package com.example.andrei.cctv;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import com.example.andrei.cctv.hikvision.DvrManager;

import org.MediaPlayer.PlayM4.Player;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private DvrManager dvrManager;

    private static final String videoPath = "rtsp://test:q123456789@192.168.1.10:554/Streaming/Channels/102";

    // IP address or static domain name of the device, the count of the characters should not more than 128
    private static final String DVR_IP = "192.168.1.10";
    private static final int DVR_PORT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        dvrManager = DvrManager.getInstance();
        dvrManager.setSurfaceHolder(surfaceView.getHolder());

//        dvrManager.initSDK();
//        dvrManager.initPlayer();
//        dvrManager.loginDevice();
//        dvrManager.dumpUsefulInfo();
//        dvrManager.initRealPlay();

        new InitializeSdkTask().execute();
    }


    @Override
    protected void onPause() {
        super.onPause();

        dvrManager.stopPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dvrManager != null) {
            dvrManager.stopPlayer();
            dvrManager.logoutDevice();
        }
    }

    private class InitializeSdkTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            dvrManager.initSDK();
            dvrManager.initPlayer();
            dvrManager.loginDevice();
            dvrManager.dumpUsefulInfo();
            //dvrManager.initRealPlay();
            return null;
        }

        @Override
        protected void onPostExecute(Object noData) {
            dvrManager.initRealPlay();
        }
    }


    //<editor-fold desc="SurfaceHolder.Callback">

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder.getSurface().isValid()) {
            if (!Player.getInstance().setVideoWindow(dvrManager.getPlayerPort(), 0, holder.getSurface())) {
                System.out.println("player set video window failed!");
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface().isValid()) {
            if (!Player.getInstance().getInstance().setVideoWindow(dvrManager.getPlayerPort(), 0, null)) {
                System.out.println("player release video window failed!");
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //</editor-fold>
}
