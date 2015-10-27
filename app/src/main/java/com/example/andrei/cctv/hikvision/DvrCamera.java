package com.example.andrei.cctv.hikvision;

public class DvrCamera {

    /***
     * Number of the camera (1-32, depending on the DVR capabilities)
     */
    private int cameraId;

    /**
     * User-friendly camera name;
     */
    private String name;

    private boolean isConnected;

    private boolean isPlaying;

    /***
     * Play port of the camera used by Player SDK to connect and stream to the camera.
     */
    private int playPort;

    /**
     * Surface on which the camera is displayed
     */
    private DvrCameraSurfaceView cameraView;

    /**
     * Camera can be shown in a grid or full-screen (with higher resolution)
     */
    private boolean showFullScreen;

    public DvrCamera() {
    }

    public DvrCamera(int cameraId, String name) {
        this.cameraId = cameraId;
        this.name = name;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean connected) {
        this.isConnected = connected;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public int getPlayPort() {
        return playPort;
    }

    public void setPlayPort(int playPort) {
        this.playPort = playPort;
    }

    public DvrCameraSurfaceView getCameraView() {
        return cameraView;
    }

    public void setCameraView(DvrCameraSurfaceView cameraView) {
        this.cameraView = cameraView;
    }

    public boolean showFullScreen() {
        return showFullScreen;
    }

    public void setShowFullScreen(boolean showFullScreen) {
        this.showFullScreen = showFullScreen;
    }

    protected void stopPlaying() {
        if (this.cameraView != null) {
            this.cameraView.stopPlaying();
            this.playPort = -1;
        }
    }
}