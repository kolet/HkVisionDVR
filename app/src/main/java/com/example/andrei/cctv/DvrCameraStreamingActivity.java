package com.example.andrei.cctv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class DvrCameraStreamingActivity extends AppCompatActivity {

    public static final String EXTRA_CAMERA_ID = "PreviewLeft";

    private int cameraId;

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dvr_camera_streaming);


        //Set the background color to black
        frameLayout = (FrameLayout) findViewById(R.id.main_background);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cameraId = extras.getInt(EXTRA_CAMERA_ID);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
