package com.hikvision.netsdk;

public class NET_DVR_IPDEVINFO_V31 {
    public byte[] byDomain;
    public byte byEnable;
    public byte byProType;
    public byte[] sPassword;
    public byte[] sUserName;
    public NET_DVR_IPADDR struIP;
    public int wDVRPort;

    public NET_DVR_IPDEVINFO_V31() {
        this.sUserName = new byte[32];
        this.sPassword = new byte[16];
        this.byDomain = new byte[64];
        this.struIP = new NET_DVR_IPADDR();
    }
}
