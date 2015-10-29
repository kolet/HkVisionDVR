package com.example.andrei.cctv;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.andrei.cctv.hikvision.HikVisionDvrManager;

public abstract class BaseDVRActivity extends Activity {

    private HikVisionDvrManager dvrManager;
    private InitializeDvrManagerTask mTask;

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
    protected void onDestroy() {
        super.onDestroy();
        //tearDown();
    }

    //    @Override
//    public void onBackPressed() {
//        tearDown();
//    }

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

    protected void tearDown() {
        // Cancel DVR SDK initialisation, if it is happening
        if (mTask != null && !mTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
            mTask.cancel(true);
            mTask = null;
        }

        // Remove all cameras
        int playPort = 0;

        // Release DVR SDK
        if (dvrManager != null) {
            dvrManager.logout(playPort);
            dvrManager = null;
        }
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
            if (result.equals("OK")) {
                dvrManager.setInitialised(true);
                onDvrInitSuccess();
            } else {
                dvrManager.setInitialised(false);
                onDvrInitFailure(result);
            }
        }
    }

    protected abstract void onDvrInitSuccess();

    protected abstract void onDvrInitFailure(String errorMessage);
}
