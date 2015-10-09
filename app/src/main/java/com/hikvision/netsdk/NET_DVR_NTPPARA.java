package com.hikvision.netsdk;

public class NET_DVR_NTPPARA extends NET_DVR_CONFIG {
    public byte byEnableNTP;
    public char cTimeDifferenceH;
    public char cTimeDifferenceM;
    public byte[] sNTPServer;
    public short wInterval;
    public short wNtpPort;

    public NET_DVR_NTPPARA() {
        this.sNTPServer = new byte[64];
    }
}
