package com.hikvision.netsdk;

public class NET_DVR_AP_INFO {
    public int dwChannel;
    public int dwMode;
    public int dwSecurity;
    public int dwSignalStrength;
    public int dwSpeed;
    public byte[] sSsid;

    public NET_DVR_AP_INFO() {
        this.sSsid = new byte[32];
    }
}
