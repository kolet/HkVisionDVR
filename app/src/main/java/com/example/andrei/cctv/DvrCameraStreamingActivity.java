package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public class DvrCameraStreamingActivity extends Activity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    private DvrCameraSurfaceView cameraView1, cameraView2;
    private HikVisionDvrManager dvrManager;


    private InitializeDvrManagerTask mTask;

    private TextView textCameraName;
    private TextView textErrorMessage;

    private DvrCamera camera1, camera2;

    private String cameraName;
    private int cameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_streaming);

        cameraView1 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera);
        cameraView2 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera2);

        textCameraName = (TextView) findViewById(R.id.text_dvr_camera_name);
        textErrorMessage = (TextView) findViewById(R.id.text_dvr_error_message);

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

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (dvrManager != null) {
//            dvrManager.stopStreaming();
//        }
//    }

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

      //  dvrManager.setPlayerView(cameraView1);
        //dvrManager.setPlayerView2(cameraView2);


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
                    addCameras();
//                    String startedStreaming = dvrManager.startStreaming();
//                    displayErrorMessage(startedStreaming);
                }

            } else {
                // Show the error message
                displayErrorMessage(result);
            }
        }

        private void addCameras() {
            camera1 = new DvrCamera(1, "CAMERA 1");
            camera1.setCameraView(cameraView1);
            camera1.setShowFullScreen(false);
            camera1.setIsConnected(true);

            dvrManager.addCamera(camera1);
            String startedStreaming = dvrManager.startStreaming(camera1);

            camera2 = new DvrCamera(2, "CAMERA 2");
            camera2.setCameraView(cameraView2);

            dvrManager.addCamera(camera2);
            dvrManager.startStreaming(camera2);
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
