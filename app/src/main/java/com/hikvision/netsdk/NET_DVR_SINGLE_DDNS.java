package com.hikvision.netsdk;

public class NET_DVR_SINGLE_DDNS {
    public byte[] byRes;
    public byte[] sDomainName;
    public byte[] sPassword;
    public byte[] sServerName;
    public byte[] sUserName;
    public int wDDNSPort;

    public NET_DVR_SINGLE_DDNS() {
        this.sUserName = new byte[32];
        this.sPassword = new byte[16];
        this.sDomainName = new byte[64];
        this.sServerName = new byte[64];
        this.byRes = new byte[16];
    }
}
