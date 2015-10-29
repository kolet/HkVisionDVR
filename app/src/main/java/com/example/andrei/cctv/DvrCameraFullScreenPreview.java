package com.example.andrei.cctv;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public class DvrCameraFullScreenPreview extends Activity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

    private DvrCamera camera;
    private DvrCameraSurfaceView cameraView;

    private int cameraId;
    private String cameraName;
    private TextView textCameraName;

    private TextView textErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_full_screen_preview);

        setupUI();
        loadExtras();
    }

    private void setupUI() {
        textCameraName = (TextView) findViewById(R.id.dvr_camera_full_screen_camera_name);
        textErrorMessage = (TextView) findViewById(R.id.dvr_camera_full_screen_message);

        cameraView = (DvrCameraSurfaceView) findViewById(R.id.dvr_camera_full_screen_camera_view);

        // HikVision SDK can be initialised before the actual camera surface is ready
        cameraView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Camera view is ready
                initDVR();

                camera = new DvrCamera(cameraId, cameraName);
                camera.setCameraView(cameraView);
                camera.setShowFullScreen(true);
                camera.setIsConnected(true);

                // remove the listener as it is no longer required
                cameraView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void loadExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cameraId = extras.getInt(EXTRA_CAMERA_ID);

            cameraName = TextUtils.isEmpty(extras.getString(EXTRA_CAMERA_NAME)) ? "" : extras.getString(EXTRA_CAMERA_NAME);
            textCameraName.setText(cameraName);
        }
    }

    private void initDVR() {
        dvrManager = HikVisionDvrManager.getInstance();

        if (dvrManager.isInitialised())
            return;

        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        // Log into the DVR only when required
        mTask = new InitializeDvrManagerTask();
        mTask.execute();
    }


    @Override
    public void onBackPressed() {
        // Shutdown SDK
        safeClose();
        super.onBackPressed();
    }

    private void safeClose() {
        // Stop streaming properly
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        int playPort = 0;

        // Release DVR SDK
        if (dvrManager != null) {
            dvrManager.logout(playPort);
            dvrManager = null;
        }

        // Remove the camera
        if (camera != null) {
            camera.stop();
            camera = null;
        }

        startActivity(new Intent(this, DvrCamerasListActivity.class));
        DvrCameraFullScreenPreview.this.finish();
    }

    private class InitializeDvrManagerTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            if (isCancelled()) {
                return "OK";
            }

            // Initialise Network SDK
            String errorMessage = dvrManager.init();

            if (errorMessage != null)
                return errorMessage;

            // Log into the DVR
            errorMessage = dvrManager.login(/*"192.168.1.10", 8000, "test", "a1234564789"*/);

            if (errorMessage != null)
                return errorMessage;

            //dvrManager.dumpUsefulInfo();
            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                dvrManager.setInitialised(true);

                if (camera != null) {
                    camera.play();
                }

            } else {
                dvrManager.setInitialised(false);
                Toast.makeText(DvrCameraFullScreenPreview.this, "ERROR INITIALISING", Toast.LENGTH_SHORT).show();
            }
        }
    }
}