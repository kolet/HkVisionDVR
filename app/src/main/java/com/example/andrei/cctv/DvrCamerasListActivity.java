package com.example.andrei.cctv;

import android.os.Bundle;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.DvrCameraArrayAdapter;

import java.util.ArrayList;

public class DvrCamerasListActivity extends BaseDVRActivity {

    private DvrCameraArrayAdapter adapter;
    private TextView textErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr_cameras_list);

        //textErrorMessage = (TextView) findViewById(R.id.dvr_camera_list_no_items);
    }

    @Override
    protected void onDvrInitSuccess() {
        cameras = new ArrayList<>();

        DvrCamera camera1 = new DvrCamera(1, "Working Cam", true);
        //DvrCamera camera2 = new DvrCamera(2, "Camera 2", true);
//        DvrCamera camera3 = new DvrCamera(3, "Camera 3");
//        DvrCamera camera4 = new DvrCamera(4, "Camera 4");

        cameras.add(camera1);
        //cameras.add(camera2);
//        cameras.add(camera3);
//        cameras.add(camera4);

//        adapter = new DvrCameraArrayAdapter(this, R.layout.gridview_item_dvr_camera, cameras);
//        gridView.setAdapter(adapter);
//
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
//        textErrorMessage.setVisibility(View.VISIBLE);
//        textErrorMessage.setText(errorMessage);
    }
}