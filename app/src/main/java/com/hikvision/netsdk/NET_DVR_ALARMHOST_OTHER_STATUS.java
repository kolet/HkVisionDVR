package com.hikvision.netsdk;

public class NET_DVR_ALARMHOST_OTHER_STATUS extends NET_DVR_CONFIG {
    public byte[] byRes;
    public byte[] bySirenStatus;

    public NET_DVR_ALARMHOST_OTHER_STATUS() {
        this.bySirenStatus = new byte[8];
        this.byRes = new byte[92];
    }
}
