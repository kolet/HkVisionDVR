package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DvrCameraSurfaceView playerView;
    private HikVisionDvrManager dvrManager;

    private InitializeDvrManagerTask dvrManagerTask;

    private TextView textErrorMessage;
    private Button buttonStart, buttonStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textErrorMessage = (TextView) findViewById(R.id.textErrorMessage);
        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);

        playerView = (DvrCameraSurfaceView) findViewById(R.id.playerView);

        //initDvrManager();
    }

    private void initDvrManager() {
        dvrManager = HikVisionDvrManager.getInstance();
        dvrManager.setPlayerView(playerView);

        if (dvrManagerTask != null && !dvrManagerTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        dvrManagerTask = new InitializeDvrManagerTask();
        dvrManagerTask.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initDvrManager();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (dvrManager != null) {
            dvrManager.stopStreaming();
            dvrManager = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dvrManager != null) {
            dvrManager.stopStreaming();
        }
    }

    private class InitializeDvrManagerTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            // Initialise Network SDK
            String errorMessage =  dvrManager.init();

            if (errorMessage != null)
                return errorMessage;

            // Log into the DVR
            errorMessage = dvrManager.login();

            if (errorMessage != null)
                return errorMessage;

            dvrManager.dumpUsefulInfo();
            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                if (dvrManager != null) {
                    String startedStreaming = dvrManager.startStreaming();
                    displayErrorMessage(startedStreaming);
                }

            } else {
                // Show the error message
                displayErrorMessage(result);
            }
        }
    }

    private void displayErrorMessage(String errorMessage) {
        if (errorMessage == null) {
            textErrorMessage.setVisibility(View.GONE);
            textErrorMessage.setText("");

            buttonStart.setEnabled(true);
            buttonStop.setEnabled(true);

        } else {
            textErrorMessage.setVisibility(View.VISIBLE);
            textErrorMessage.setText(errorMessage);

            buttonStart.setEnabled(true);
            buttonStop.setEnabled(false);
        }
    }
}
