package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
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

    private RelativeLayout parentLayout;

    private ArrayList<DvrCamera> cameras = new ArrayList<>();
    private final int CAMERAS_NUMBER = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_cameras_grid);

        parentLayout = (RelativeLayout) findViewById(R.id.layout_dvr_camera_list);
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

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_dvr_cameras);
        gridLayout.removeAllViews();

        gridLayout.setColumnCount(2);
        //int rows = CAMERAS_NUMBER / 2;
        gridLayout.setRowCount(CAMERAS_NUMBER / 2);

        for (int i = 0, col = 0, row = 0; i < CAMERAS_NUMBER; i++, col++) {
            if (col == 2) {
                // start new row
                col = 0;
                row++;
            }

            // Add a camera preview to the grid programmatically
            DvrCameraSurfaceView cameraView = new DvrCameraSurfaceView(this);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = outMetrics.widthPixels / 2 - 4;
            params.height = 360;
            params.leftMargin = 2;
            params.rightMargin = 2;
            params.topMargin = 2;
            params.bottomMargin = 2;

            params.setGravity(Gravity.CENTER);

            params.columnSpec = GridLayout.spec(col);
            params.rowSpec = GridLayout.spec(row);
            cameraView.setLayoutParams(params);

            gridLayout.addView(cameraView);

            int cameraId = i + 1;
            DvrCamera camera = new DvrCamera(cameraId, "Camera " + cameraId, true);
            camera.setCameraView(cameraView);
            camera.setShowFullScreen(false);

            cameras.add(camera);
        }
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
