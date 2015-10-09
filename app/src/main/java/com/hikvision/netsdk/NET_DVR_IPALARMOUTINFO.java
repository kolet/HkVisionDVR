package com.hikvision.netsdk;

public class NET_DVR_IPALARMOUTINFO {
    public byte byAlarmOut;
    public byte byIPID;
    public byte[] byRes;

    public NET_DVR_IPALARMOUTINFO() {
        this.byRes = new byte[18];
    }
}
