package com.example.andrei.cctv.todo.dvr;

import android.app.Fragment;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.andrei.cctv.R;

public class CameraViewFragment extends Fragment {
    private SurfaceView surfaceView;

    public CameraViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        createPlayer();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        surfaceView = (SurfaceView) getActivity().findViewById(R.id.surfaceView);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (holder.getSurface().isValid()) {
                    if (!PlayerManager.getPlayer().setVideoWindow(PlayerManager.getPlayer().getPort(), 0, holder.getSurface())) {
                        System.out.println("player set video window failed!");
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (holder.getSurface().isValid()) {
                    if (!PlayerManager.getPlayer().setVideoWindow(PlayerManager.getPlayer().getPort(), 0, null)) {
                        System.out.println("player release video window failed!");
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        createPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (getActivity().isChangingConfigurations()) {
            // configuration is changing, keep streaming video
        } else {
            PlayerManager.stopPlayer();
        }
    }


    private void createPlayer() {
       // DvrManager_Future.getInstance().initRealPlay();

        if (surfaceView != null) {
            PlayerManager.setSurfaceHolder(surfaceView.getHolder());
            PlayerManager.initPlayer();
        }

    }

}
