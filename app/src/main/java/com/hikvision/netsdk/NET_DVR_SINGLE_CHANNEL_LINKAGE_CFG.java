package com.hikvision.netsdk;

public class NET_DVR_SINGLE_CHANNEL_LINKAGE_CFG {
    public byte byAddressType;
    public byte byRes1;
    public byte[] byRes2;
    public byte[] byRes3;
    public int dwChannel;
    public byte[] sPassword;
    public byte[] sUserName;
    public NET_DVR_IPADDR struIp;
    public byte[] szDomain;
    public int wPort;

    public NET_DVR_SINGLE_CHANNEL_LINKAGE_CFG() {
        this.szDomain = new byte[64];
        this.byRes2 = new byte[80];
        this.struIp = new NET_DVR_IPADDR();
        this.sUserName = new byte[32];
        this.sPassword = new byte[16];
        this.byRes3 = new byte[64];
    }
}
