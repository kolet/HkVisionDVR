package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.andrei.cctv.hikvision.DvrManager;

import org.MediaPlayer.PlayM4.Player;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private DvrManager dvrManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        dvrManager = DvrManager.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startStreaming();
    }

    private void startStreaming() {
        new InitializeDvrManagerTask().execute(surfaceView.getHolder());
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopStreaming();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopStreaming();
//    }

    private void stopStreaming() {
        if (dvrManager != null) {
            dvrManager.stopPlayer();
            dvrManager.logoutDevice();
        }
    }

    private class InitializeDvrManagerTask extends AsyncTask<SurfaceHolder, Void, Boolean> {

        @Override
        protected Boolean doInBackground(SurfaceHolder... params) {
            dvrManager.initSDK(params[0]);
            dvrManager.initPlayer();
            dvrManager.loginDevice();
            dvrManager.dumpUsefulInfo();
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            dvrManager.initRealPlay();
        }
    }


    //<editor-fold desc="SurfaceHolder.Callback">

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!holder.getSurface().isValid()) return;

        Player player = Player.getInstance();

        if (!player.setVideoWindow(player.getPort(), 0, holder.getSurface())) {
            System.out.println("player set video window failed!");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (!holder.getSurface().isValid()) return;

        Player player = Player.getInstance();

        if (!player.setVideoWindow(player.getPort(), 0, null)) {
            System.out.println("player release video window failed!");
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //</editor-fold>
}
