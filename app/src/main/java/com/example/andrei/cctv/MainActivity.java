package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import org.MediaPlayer.PlayM4.Player;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SurfaceView surfaceView;
    private HikVisionDvrManager dvrManager;

    private InitializeDvrManagerTask initTask;

    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.buttonStart);

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        stopStreaming();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopStreaming();
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        stopStreaming();
//    }

    private void stopStreaming() {
        if (dvrManager != null) {
            dvrManager.stopStreaming();
            dvrManager.stopPlayer();
            dvrManager.logoutDevice();
        }
    }

    private class InitializeDvrManagerTask extends AsyncTask<SurfaceHolder, Void, String> {

        @Override
        protected String doInBackground(SurfaceHolder... params) {
            String errorMessage = "Failure";

            if (!dvrManager.initPlayer())
                return errorMessage;

            if (!dvrManager.initSDK())
                return errorMessage;

            if ((errorMessage = dvrManager.loginDevice())  != null) {
                // Login failed
                return errorMessage;
            }

            dvrManager.setSurfaceHolder(params[0]);
            //dvrManager.dumpUsefulInfo();
            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                dvrManager.startStreaming();
            } else {
                // Show the error message


                buttonStart.setEnabled(false);

            }
        }
    }

    //<editor-fold desc="SurfaceHolder.Callback">

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       // if (!holder.getSurface().isValid()) return;

        Player player = Player.getInstance();

        if (!player.setVideoWindow(player.getPort(), 0, holder.getSurface())) {
            System.out.println("player set video window failed!");
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      //  if (!holder.getSurface().isValid()) return;

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
