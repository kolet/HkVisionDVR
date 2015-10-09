package com.hikvision.netsdk;

public class NET_DVR_UPNP_NAT_STATE {
    public NET_DVR_UPNP_PORT_STATE[] strUpnpPort;

    public NET_DVR_UPNP_NAT_STATE() {
        this.strUpnpPort = new NET_DVR_UPNP_PORT_STATE[12];
        for (int i = 0; i < 12; i++) {
            this.strUpnpPort[i] = new NET_DVR_UPNP_PORT_STATE();
        }
    }
}
