package com.hikvision.netsdk;

public class NET_DVR_ETHERNET_V30 {
    public byte[] byMACAddr;
    public int dwNetInterface;
    public NET_DVR_IPADDR struDVRIP;
    public NET_DVR_IPADDR struDVRIPMask;
    public int wDVRPort;
    public int wMTU;

    public NET_DVR_ETHERNET_V30() {
        this.struDVRIP = new NET_DVR_IPADDR();
        this.struDVRIPMask = new NET_DVR_IPADDR();
        this.byMACAddr = new byte[6];
    }
}
