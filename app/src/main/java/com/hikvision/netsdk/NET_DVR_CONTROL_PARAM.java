package com.hikvision.netsdk;

public class NET_DVR_CONTROL_PARAM extends NET_DVR_CONTROL {
    public byte byIndex;
    public byte byRes1;
    public byte[] byRes2;
    public int dwControlParam;
    public byte[] sDeviceID;
    public int wChan;

    public NET_DVR_CONTROL_PARAM() {
        this.sDeviceID = new byte[32];
        this.byRes2 = new byte[32];
    }
}
