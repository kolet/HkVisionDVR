package com.example.andrei.cctv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraSurfaceView;

import java.util.ArrayList;

public class DvrCamerasListActivity extends BaseDVRActivity {

    private GridLayout gridLayout;
    private TextView textErrorMessage;
    private DisplayMetrics outMetrics;

    private final int CAMERAS_NUMBER = 4;
    private final int NUM_OF_COLUMNS = 2;

    protected ArrayList<DvrCamera> cameras = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_cameras_list);

        setupUI();
        createCameraGrid();
    }

    private void setupUI() {
        //textErrorMessage = (TextView) findViewById(R.id.dvr_camera_list_no_items);
        gridLayout = (GridLayout) findViewById(R.id.grid_dvr_cameras);

        // Calculate display dimensions - we will have two camera previews in a row
        Display display = getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= 17) {
            display.getRealMetrics(outMetrics);
        } else {
            display.getMetrics(outMetrics);
        }
    }

    private void createCameraGrid() {
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

            cameraView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tearDown();
                    clearCameras();

                    DvrCameraSurfaceView cv = (DvrCameraSurfaceView) v;

                    Intent intent = new Intent(DvrCamerasListActivity.this, DvrCameraFullScreenPreview.class);
                    intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_ID,  cv.getCameraId());
                    //intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_NAME, item.getName());

                    startActivity(intent);
                }
            });

            gridLayout.addView(cameraView);
        }

        Toast.makeText(DvrCamerasListActivity.this, "Grid of cameras created", Toast.LENGTH_SHORT).show();
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

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
////        fillCameraGrid();
//    }

    @Override
    protected void onDvrInitSuccess() {
       if (cameras == null || cameras.size() == 0) {
           // TODO: fallback gracefully
           Toast.makeText(DvrCamerasListActivity.this, "Cameras are not ready yet!", Toast.LENGTH_SHORT).show();
           return;
       }

        for (DvrCamera camera : cameras) {
            camera.play();
        }


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                DvrCamera item = (DvrCamera) adapterView.getItemAtPosition(position);
//
//                // Only show the full-screen camera preview if it is connected
//                if (item.isConnected()) {
//                    Intent intent = new Intent(DvrCamerasListActivity.this, DvrCameraFullScreenPreview.class);
//
//                    intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_ID, item.getCameraId());
//                    intent.putExtra(DvrCameraFullScreenPreview.EXTRA_CAMERA_NAME, item.getName());
//
//                    startActivity(intent);
//
//
//                    //overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
//                }
//            }
//        });
    }

    @Override
    protected void onDvrInitFailure(String errorMessage) {
        Toast.makeText(DvrCamerasListActivity.this, "ERROR: " + errorMessage, Toast.LENGTH_SHORT).show();
//        textErrorMessage.setVisibility(View.VISIBLE);
//        textErrorMessage.setText(errorMessage);
    }
}