package com.hikvision.netsdk;

public class NET_DVR_WIFIETHERNET {
    public byte[] byMACAddr;
    public int dwAutoDns;
    public int dwEnableDhcp;
    public byte[] sFirstDns;
    public byte[] sGatewayIpAddr;
    public byte[] sIpAddress;
    public byte[] sIpMask;
    public byte[] sSecondDns;

    public NET_DVR_WIFIETHERNET() {
        this.sIpAddress = new byte[16];
        this.sIpMask = new byte[16];
        this.byMACAddr = new byte[6];
        this.sFirstDns = new byte[16];
        this.sSecondDns = new byte[16];
        this.sGatewayIpAddr = new byte[16];
    }
}
