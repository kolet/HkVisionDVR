package com.hikvision.netsdk;

public class NET_DVR_UPNP_PORT_STATE {
    public int dwEnabled;
    public int dwStatus;
    public NET_DVR_IPADDR struNatExternalIp;
    public NET_DVR_IPADDR struNatInternalIp;
    public int wExternalPort;
    public int wInternalPort;

    public NET_DVR_UPNP_PORT_STATE() {
        this.struNatExternalIp = new NET_DVR_IPADDR();
        this.struNatInternalIp = new NET_DVR_IPADDR();
    }
}
