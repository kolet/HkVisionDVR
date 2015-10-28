package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import java.util.ArrayList;

public class Test1Activity extends Activity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

    private LinearLayout parentLayout;

    private ArrayList<DvrCamera> cameras = new ArrayList<>();
    private final int CAMERAS_NUMBER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_streaming);

        parentLayout = (LinearLayout) findViewById(R.id.layout_dvr_camera_list);
        initCameras();
    }

    private void initCameras() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(outMetrics);
        } else {
            display.getMetrics(outMetrics);
        }

        for (int i = 1; i <= CAMERAS_NUMBER; i++) {
            FrameLayout childLayout = new FrameLayout(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(320, 200);
            params.setMargins(8, 8, 8, 8);

            childLayout.setLayoutParams(params);

            DvrCameraSurfaceView cameraView = new DvrCameraSurfaceView(this);
            RelativeLayout.LayoutParams cameraViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            cameraView.setLayoutParams(cameraViewParams);
            childLayout.addView(cameraView);
//
            DvrCamera camera = new DvrCamera(i, "CAMERA " + i);
            camera.setCameraView(cameraView);
            camera.setShowFullScreen(false);
            camera.setIsConnected(true);

            cameras.add(camera);


//            <com.example.andrei.cctv.hikvision.DvrCameraSurfaceView
//            android:id="@+id/player_dvr_camera2"
//            android:layout_width="match_parent"
//            android:layout_height="match_parent" />


            parentLayout.addView(childLayout);
        }

//        DvrCameraSurfaceView cameraView1 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera);
//        DvrCamera camera1 = new DvrCamera(1, "CAMERA 1");
//        camera1.setCameraView(cameraView1);
//        camera1.setShowFullScreen(false);
//        camera1.setIsConnected(true);
//
//        DvrCameraSurfaceView cameraView2 = (DvrCameraSurfaceView) findViewById(R.id.player_dvr_camera2);
//
//        DvrCamera camera2 = new DvrCamera(2, "CAMERA 2");
//        camera2.setCameraView(cameraView2);
//        camera2.setShowFullScreen(false);
//        camera2.setIsConnected(false);
//
//        cameras.add(camera1);
//        cameras.add(camera2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDVR();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // tearDown();
    }


    @Override
    public void onBackPressed() {
        tearDown();
    }

    private void initDVR() {
        dvrManager = HikVisionDvrManager.getInstance();

        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        mTask = new InitializeDvrManagerTask();
        mTask.execute();
    }

    private void tearDown() {
        // Cancel DVR SDK initialisation, if it is happening
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        // Release DVR SDK
        if (dvrManager != null) {
            if (cameras.size() > 0) {
                // we could stop streaming
                dvrManager.logout(cameras.get(0).getPlayPort());

                for (DvrCamera camera : cameras) {
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
            if (isCancelled()) {
                return "OK";
            }

            // Initialise Network SDK
            String errorMessage = dvrManager.init();

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
//        if (errorMessage == null) {
//            textErrorMessage.setVisibility(View.GONE);
//            textErrorMessage.setText("");
//
//        } else {
//            textErrorMessage.setVisibility(View.VISIBLE);
//            textErrorMessage.setText(errorMessage);
//        }
    }
}
