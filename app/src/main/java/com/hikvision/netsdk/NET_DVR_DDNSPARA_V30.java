package com.hikvision.netsdk;

public class NET_DVR_DDNSPARA_V30 extends NET_DVR_CONFIG {
    public byte byEnableDDNS;
    public byte byHostIndex;
    public byte[] byRes1;
    public byte[] byRes2;
    public NET_DVR_SINGLE_DDNS[] struDDNS;

    public NET_DVR_DDNSPARA_V30() {
        this.byRes1 = new byte[2];
        this.struDDNS = new NET_DVR_SINGLE_DDNS[10];
        this.byRes2 = new byte[16];
        for (int i = 0; i < 10; i++) {
            this.struDDNS[i] = new NET_DVR_SINGLE_DDNS();
        }
    }
}
