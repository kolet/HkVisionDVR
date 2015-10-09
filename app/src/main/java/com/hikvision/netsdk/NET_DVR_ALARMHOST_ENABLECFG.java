package com.hikvision.netsdk;

public class NET_DVR_ALARMHOST_ENABLECFG extends NET_DVR_CONFIG {
    public byte[] byAudioOutEnable;
    public byte[] byElectroLockEnable;
    public byte[] byMobileGateEnable;
    public byte[] byRes;
    public byte bySerialPurpose;
    public byte[] bySirenEnable;

    public NET_DVR_ALARMHOST_ENABLECFG() {
        this.byAudioOutEnable = new byte[32];
        this.byElectroLockEnable = new byte[32];
        this.byMobileGateEnable = new byte[32];
        this.bySirenEnable = new byte[8];
        this.byRes = new byte[63];
    }
}
