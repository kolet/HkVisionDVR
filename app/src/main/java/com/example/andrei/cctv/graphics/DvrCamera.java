package com.example.andrei.cctv.graphics;

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
}