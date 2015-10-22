package com.example.andrei.cctv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.andrei.cctv.graphics.DvrCamera;
import com.example.andrei.cctv.graphics.DvrCameraArrayAdapter;

import java.util.ArrayList;

public class DvrCamerasListActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<DvrCamera> cameras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr_cameras_list);

        gridView = (GridView) findViewById(R.id.grid_dvr_camera_list);

        setDummyData();

        DvrCameraArrayAdapter adapter = new DvrCameraArrayAdapter(this, R.layout.gridview_item_dvr_camera, cameras);
        gridView.setAdapter(adapter);

    }

    private void setDummyData() {
        cameras = new ArrayList<>();

        DvrCamera camera1 = new DvrCamera(1, "Camera 1");
        DvrCamera camera2 = new DvrCamera(2, "Camera 2");

        cameras.add(camera1);
        cameras.add(camera2);
    }
}
