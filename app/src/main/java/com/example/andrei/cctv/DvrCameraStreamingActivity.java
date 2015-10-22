package com.example.andrei.cctv;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public class DvrCameraStreamingActivity extends Activity {

    public static final String EXTRA_CAMERA_ID = "CameraID";
    public static final String EXTRA_CAMERA_NAME = "CameraName";

    private DvrCameraSurfaceView playerView;
    private HikVisionDvrManager dvrManager;

    private DvrCameraSurfaceView surfaceView;

    private TextView textCameraName;

    private String cameraName;
    private int cameraId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dvr_camera_streaming);

        textCameraName = (TextView) findViewById(R.id.text_dvr_camera_name);

        loadExtras();
    }

    private void loadExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            cameraId = extras.getInt(EXTRA_CAMERA_ID);

            cameraName = extras.getString(EXTRA_CAMERA_NAME);
            if (!TextUtils.isEmpty(cameraName)) {
                textCameraName.setText(cameraName);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
