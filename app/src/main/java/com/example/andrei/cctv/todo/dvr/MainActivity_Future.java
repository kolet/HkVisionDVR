package com.example.andrei.cctv.todo.dvr;

import android.app.Activity;
import android.os.Bundle;

import com.example.andrei.cctv.R;

public class MainActivity_Future extends Activity {

    private static final String TAG = MainActivity_Future.class.getSimpleName();
    private static final String FRAGMENT_TAG = "CameraViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // new InitializeSdkTask().execute();
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        final DvrManager_Future dvrManager = DvrManager_Future.getInstance();
//
//        if (dvrManager != null) {
//            PlayerManager.stopPlayer();
//            dvrManager.stopRealPlay();
//            dvrManager.logoutDevice();
//        }
//    }
//
//    private class InitializeSdkTask extends AsyncTask {
//        @Override
//        protected Object doInBackground(Object[] params) {
//            final DvrManager_Future dvrManager = DvrManager_Future.getInstance();
//
//            dvrManager.initSDK();
//            dvrManager.loginDevice();
//            dvrManager.dumpUsefulInfo();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Object noData) {
//            Fragment f = getFragmentManager().findFragmentByTag(FRAGMENT_TAG);
//
//            if (f == null) {
//                getFragmentManager().beginTransaction()
//                        .add(new CameraViewFragment(), FRAGMENT_TAG)
//                        .commit();
//            }
//        }
//    }
}
