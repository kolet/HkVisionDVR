package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import org.MediaPlayer.PlayM4.Player;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private HikVisionDvrManager dvrManager;

    private InitializeDvrManagerTask initTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);

        initDvrManager();
    }

    private void initDvrManager() {
        dvrManager = HikVisionDvrManager.getInstance();

        if (initTask != null && !initTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        initTask = new InitializeDvrManagerTask();
        initTask.execute(surfaceView.getHolder());
    }

    @Override
    protected void onStart() {
        super.onStart();
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
