package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import java.util.ArrayList;

public class DvrCameraStreamingActivity extends Activity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    //private DvrCameraSurfaceView cameraView1, cameraView2;
    private HikVisionDvrManager dvrManager;

    private InitializeDvrManagerTask mTask;

    private TextView textCameraName;
    private TextView textErrorMessage;

    //private DvrCamera camera1, camera2;

    private ArrayList<DvrCamera> cameras = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_streaming);

        initCameras();

        textCameraName = (TextView) findViewById(R.id.text_dvr_camera_name);
        textErrorMessage = (TextView) findViewById(R.id.text_dvr_error_message);

    }

    private void initCameras() {
        DvrCameraSurfaceView cameraView1 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera);
        DvrCamera camera1 = new DvrCamera(1, "CAMERA 1");
        camera1.setCameraView(cameraView1);
        camera1.setShowFullScreen(false);
        camera1.setIsConnected(true);

        DvrCameraSurfaceView cameraView2 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera2);

        DvrCamera camera2 = new DvrCamera(2, "CAMERA 2");
        camera2.setCameraView(cameraView2);
        camera2.setShowFullScreen(false);
        camera2.setIsConnected(false);

        cameras.add(camera1);
        cameras.add(camera2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initStreaming();
    }

    @Override
    protected void onStop() {
        super.onStop();
       // stopStreaming();
    }


    @Override
    public void onBackPressed() {
        stopStreaming();
    }

    private void initStreaming() {
        dvrManager = HikVisionDvrManager.getInstance();

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
            if (cameras.size() > 0) {
                // we could stop streaming
                dvrManager.stopStreaming(cameras.get(0).getPlayPort());

                for (DvrCamera camera: cameras) {
                    camera.stop();
                    camera = null;
                }
            }

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
            if (!result.equals("OK")) {
                displayErrorMessage(result);
                return;
            }

            for (DvrCamera camera : cameras) {
                camera.play();
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
