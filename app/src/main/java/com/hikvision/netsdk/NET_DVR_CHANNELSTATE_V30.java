package com.hikvision.netsdk;

public class NET_DVR_CHANNELSTATE_V30 {
    public byte byExceedMaxLink;
    public byte byHardwareStatic;
    public byte byRecordStatic;
    public byte bySignalStatic;
    public int dwBitRate;
    public int dwIPLinkNum;
    public int dwLinkNum;
    public NET_DVR_IPADDR[] struClientIP;

    public NET_DVR_CHANNELSTATE_V30() {
        this.struClientIP = new NET_DVR_IPADDR[6];
        for (int i = 0; i < 6; i++) {
            this.struClientIP[i] = new NET_DVR_IPADDR();
        }
    }
}
