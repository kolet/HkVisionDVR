package com.example.andrei.cctv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import java.util.ArrayList;

public class DvrCamerasListActivity extends Activity {

    private GridLayout gridLayout;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private TextView textErrorMessage;
    private DisplayMetrics outMetrics;

    private final int CAMERAS_NUMBER = 4;
    private final int NUM_OF_COLUMNS = 2;

    protected ArrayList<DvrCamera> cameras = new ArrayList<>();

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_cameras_list);

        setupUI();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        refreshGrid();
//    }

//    @Override
//    protected void onStop() {
//        safeClose();
//        super.onStop();
//    }

    //    @Override
//    protected void onStop() {
//        safeClose();
//        super.onStop();
//    }

    @Override
    public void onBackPressed() {
        safeClose();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshGrid();
    }

    private void setupUI() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.dvr_cameras_coordinator_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_dvr_cameras);
        gridLayout = (GridLayout) findViewById(R.id.dvr_cameras_grid);

        // Calculate display dimensions - we will have two camera previews in a row
        Display display = getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(outMetrics);
        } else {
            display.getMetrics(outMetrics);
        }

//        refreshGrid();
    }

    private void refreshGrid() {
        // Create a grid of cameras
        clearCameras();
        gridLayout.removeAllViews();

        gridLayout.setColumnCount(NUM_OF_COLUMNS);
        gridLayout.setRowCount(CAMERAS_NUMBER / NUM_OF_COLUMNS);

        for (int i = 0, col = 0, row = 0; i < CAMERAS_NUMBER; i++, col++) {
            if (col == NUM_OF_COLUMNS) {
                // start new row
                col = 0;
                row++;
            }

            // Add a camera preview to the grid programmatically
            DvrCameraSurfaceView cameraView = new DvrCameraSurfaceView(this);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = outMetrics.widthPixels / NUM_OF_COLUMNS - 4;
            params.height = 360;
            params.leftMargin = 2;
            params.rightMargin = 2;
            params.topMargin = 2;
            params.bottomMargin = 2;

            params.setGravity(Gravity.CENTER);

            params.columnSpec = GridLayout.spec(col);
            params.rowSpec = GridLayout.spec(row);
            cameraView.setLayoutParams(params);

            // Add a camera in the grid cell
            final int cameraId = i + 1;
            DvrCamera camera = new DvrCamera(cameraId, "Camera " + cameraId, true);
            camera.setCameraView(cameraView);
            camera.setShowFullScreen(false);
            cameras.add(camera);
            cameraView.setCameraId(cameraId);

            // Show full screen view on a camera click
            cameraView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    safeClose();

                    DvrCameraSurfaceView cv = (DvrCameraSurfaceView) v;

                    Intent intent = new Intent(DvrCamerasListActivity.this, DvrCameraFullScreenPreview.class);
                    intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_ID, cv.getCameraId());
                    //intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_NAME, item.getName());

                    startActivity(intent);
                    finish();
                }
            });

            gridLayout.addView(cameraView);
        }

        // Initialise SDK only when the grid is ready
        gridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                initDVR();

                gridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
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

    private void safeClose() {
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        // Shut down the SDK
        int playPort = 0;

        // Release DVR SDK
        if (dvrManager != null) {
            dvrManager.logout(playPort);
            dvrManager = null;
        }

        // and stop all previews
        clearCameras();
    }

    protected void clearCameras() {
        if (cameras == null || cameras.size() == 0) {
            return;
        }

        for (DvrCamera camera : cameras) {
            camera.stop();
            camera = null;
        }

        cameras.clear();
    }

    private class InitializeDvrManagerTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

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
            errorMessage = dvrManager.login(/*"192.168.1.10", 8000, "test", "a123456789"*/);

            if (errorMessage != null)
                return errorMessage;

            //dvrManager.dumpUsefulInfo();
            return "OK";
        }

        @Override
        protected void onPostExecute(String message) {
            progressBar.setVisibility(View.GONE);

            if (message.equals("OK")) {
                dvrManager.setInitialised(true);
                onDvrInitSuccess();
            } else {
                dvrManager.setInitialised(false);
                onDvrInitFailure(message);
            }
        }

        private void onDvrInitSuccess() {
            for (DvrCamera camera : cameras) {
                camera.play();
            }
        }

        private void onDvrInitFailure(String errorMessage) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initDVR();
                        }
                    })
                    .setActionTextColor(Color.WHITE);

            View view = snackbar.getView();
            view.setBackgroundColor(getResources().getColor(R.color.bg_title_stroke_color));

            snackbar.show();
        }
    }
}