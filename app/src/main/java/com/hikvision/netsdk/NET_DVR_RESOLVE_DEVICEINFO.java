package com.hikvision.netsdk;

public class NET_DVR_RESOLVE_DEVICEINFO {
    public int dwPort;
    public byte[] sGetIP;

    public NET_DVR_RESOLVE_DEVICEINFO() {
        this.sGetIP = new byte[64];
    }
}
