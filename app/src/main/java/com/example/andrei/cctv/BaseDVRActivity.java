package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.andrei.cctv.hikvision.DvrCamera;
import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

import java.util.ArrayList;

public abstract class BaseDVRActivity extends Activity {

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

    protected ArrayList<DvrCamera> cameras = new ArrayList<>();

    private boolean isInitialised;

    @Override
    protected void onStart() {
        super.onStart();
        initDVR();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        initDVR();
//    }

    @Override
    protected void onPause() {
        super.onPause();
//        tearDown();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tearDown();
    }

    //    @Override
//    public void onBackPressed() {
//        tearDown();
//    }

    private void initDVR() {
        dvrManager = HikVisionDvrManager.getInstance();

        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            return;
        }

        mTask = new InitializeDvrManagerTask();
        mTask.execute();
    }

    protected void tearDown() {
        // Cancel DVR SDK initialisation, if it is happening
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        // Release DVR SDK
        if (dvrManager != null) {
            int playPort = 0;

            if (cameras != null && cameras.size() > 0) {
                playPort = cameras.get(0).getPlayPort();
            }

            clearCameras();

            dvrManager.logout(playPort);
            dvrManager = null;

            isInitialised = false;
        }
    }

    protected void clearCameras() {
        if (cameras == null || cameras.size() == 0) {
            return;
        }

        for (DvrCamera camera : cameras) {
            if (camera.getCameraView() != null) {
                camera.getCameraView().stopPlaying();
                camera.setCameraView(null);

            }

            camera = null;
        }

        cameras.clear();
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

            //dvrManager.dumpUsefulInfo();
            return "OK";
        }

        @Override
        protected void onPostExecute(String result) {
            isInitialised = result.equals("OK");

            if (!isInitialised) {
                onDvrInitFailure(result);
                return;
            } else {
                // Do something, like ask cameras to play
                onDvrInitSuccess();
            }
        }
    }

//    protected abstract void addCamera();

    protected abstract void onDvrInitSuccess();

    protected abstract void onDvrInitFailure(String errorMessage);
}
