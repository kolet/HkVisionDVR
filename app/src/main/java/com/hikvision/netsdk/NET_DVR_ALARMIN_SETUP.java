package com.hikvision.netsdk;

public class NET_DVR_ALARMIN_SETUP {
    public byte[] byAssiciateAlarmIn;
    public byte[] byRes;

    public NET_DVR_ALARMIN_SETUP() {
        this.byAssiciateAlarmIn = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byRes = new byte[100];
    }
}
