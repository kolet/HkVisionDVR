package com.example.andrei.cctv;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;

public class CameraPreviewTestActivity extends Activity implements MediaPlayer.OnPreparedListener,
        SurfaceHolder.Callback {

    private static final String videoPath = "rtsp://test:a123456789@192.168.1.10:554/Streaming/Channels/102";
    private static final String videoPath1 = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    private static final String videoPath2 = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
    private static final String videoPath3 = "rtsp://test:a123456789@192.168.1.10:554/h264/ch1/sub/av_stream";

    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview_test);

//        VideoView videoView = (VideoView) findViewById(R.id.videoView);
//
//        videoView.setVideoURI(Uri.parse(videoPath3));
//        videoView.requestFocus();
//        videoView.start();

// Configure the view that renders live video.
        SurfaceView surfaceView =
                (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setFixedSize(320, 240);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        Context context = getApplicationContext();
        Map<String, String> headers = getRtspHeaders();
        Uri source = Uri.parse(videoPath3);

        try {
            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);

            _mediaPlayer.setAudioStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE);

            try {
                // Specify the IP camera's URL and auth headers.
                _mediaPlayer.setDataSource(context, source, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }

            _mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    System.out.print("ERROR " + what);
                    return false;
                }
            });




            try {
                _mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        _mediaPlayer.release();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }

    private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        String basicAuthValue = getBasicAuthValue("test", "a1234567489");
        headers.put("Authorization", basicAuthValue);
        return headers;
    }

    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }
}
