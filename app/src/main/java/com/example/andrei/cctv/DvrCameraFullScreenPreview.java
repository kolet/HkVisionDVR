package com.example.andrei.cctv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public class DvrCameraFullScreenPreview extends AppCompatActivity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

    private DvrCameraSurfaceView playerView;
    private TextView textCameraName;
    private TextView textErrorMessage;

    private String cameraName;
    private int cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_full_screen_preview);

        playerView = (DvrCameraSurfaceView) findViewById(R.id.dvr_camera_full_screen_camera_view);
        textCameraName = (TextView) findViewById(R.id.dvr_camera_full_screen_camera_name);
        textErrorMessage = (TextView) findViewById(R.id.dvr_camera_full_screen_message);

        loadExtras();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initStreaming();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopStreaming();
    }

    @Override
    public void onBackPressed() {
        stopStreaming();
    }

    private void loadExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cameraId = extras.getInt(EXTRA_CAMERA_ID);

            cameraName = extras.getString(EXTRA_CAMERA_NAME);
            if (!TextUtils.isEmpty(cameraName)) {
                textCameraName.setText(cameraName);
            }
        }
    }

    private void initStreaming() {
        dvrManager = HikVisionDvrManager.getInstance();
        dvrManager.setPlayerView(playerView);


        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        mTask = new InitializeDvrManagerTask();
        mTask.execute();
    }

    private void stopStreaming() {
        // Cancel DVR SDK initialisation, if it is happening
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        // Release DVR SDK
        if (dvrManager != null) {
            dvrManager.stopStreaming();
            dvrManager = null;
        }

        finish();
    }

    private class InitializeDvrManagerTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            if (isCancelled()){
                return "OK";
            }

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

        } else {
            textErrorMessage.setVisibility(View.VISIBLE);
            textErrorMessage.setText(errorMessage);
        }
    }
}
