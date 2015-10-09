package com.hikvision.netsdk;

public class NET_DVR_SERVER_DEVICE_CFG {
    public byte byDeviceID;
    public byte[] byDeviceName;
    public byte byDeviceType;
    public byte[] byRes;

    public NET_DVR_SERVER_DEVICE_CFG() {
        this.byDeviceName = new byte[32];
        this.byRes = new byte[6];
    }
}
