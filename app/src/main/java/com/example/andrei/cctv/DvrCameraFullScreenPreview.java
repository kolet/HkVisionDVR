package com.example.andrei.cctv;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;

public class DvrCameraFullScreenPreview extends BaseDVRActivity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

//    private HikVisionDvrManager dvrManager;
//    private InitializeDvrManagerTask mTask;

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

        cameraView = (DvrCameraSurfaceView) findViewById(R.id.dvr_camera_full_screen_camera_view);
        textCameraName = (TextView) findViewById(R.id.dvr_camera_full_screen_camera_name);
        textErrorMessage = (TextView) findViewById(R.id.dvr_camera_full_screen_message);

        loadExtras();
        addCameras();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        initDVR();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        tearDown();
//    }
//
    @Override
    public void onBackPressed() {
        // Stop streaming properly
        super.tearDown();
        finish();
    }

    private void loadExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cameraId = extras.getInt(EXTRA_CAMERA_ID);

            cameraName = TextUtils.isEmpty(extras.getString(EXTRA_CAMERA_NAME)) ? "NONAME" : extras.getString(EXTRA_CAMERA_NAME);
            textCameraName.setText(cameraName);
        }
    }
//
//    private void initDVR() {
//        dvrManager = HikVisionDvrManager.getInstance();
//
//        camera = new DvrCamera(cameraId, cameraName);
//        camera.setCameraView(cameraView);
//        camera.setShowFullScreen(true);
//        camera.setIsConnected(true);
//
//        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
//            return;
//        }
//
//        mTask = new InitializeDvrManagerTask();
//        mTask.execute();
//    }

//    private void tearDown() {
//        // Cancel DVR SDK initialisation, if it is happening
//        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
//            mTask.cancel(true);
//            mTask = null;
//        }
//
//        // Release DVR SDK
//        if (dvrManager != null) {
//            int playPort = camera.getPlayPort();
//
//            if (camera != null) {
//                camera.stop();
//                camera = null;
//            }
//
//            dvrManager.logout(playPort);
//            dvrManager = null;
//        }
//
//        finish();
//    }

    @Override
    protected void onDvrInitFailure(String errorMessage) {
        if (errorMessage == null) {
            textErrorMessage.setVisibility(View.GONE);
            textErrorMessage.setText("");

        } else {
            textErrorMessage.setVisibility(View.VISIBLE);
            textErrorMessage.setText(errorMessage);
        }
    }

    @Override
    protected void addCameras() {
        camera = new DvrCamera(cameraId, cameraName);
        camera.setCameraView(cameraView);
        camera.setShowFullScreen(true);
        camera.setIsConnected(true);

        cameras.add(camera);
    }

    @Override
    protected void onDvrInitSuccess() {
        if (camera != null) {
            camera.play();
        }
    }

}