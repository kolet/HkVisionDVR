package com.hikvision.netsdk;

public class NET_DVR_ALARMHOST_MAIN_STATUS extends NET_DVR_CONFIG {
    public byte[] byAlarmInFaultStatus;
    public byte[] byAlarmInStatus;
    public byte[] byAlarmOutStatus;
    public byte[] byBypassStatus;
    public byte[] byRes;
    public byte[] bySetupAlarmStatus;
    public byte[] bySubSystemGuardStatus;

    public NET_DVR_ALARMHOST_MAIN_STATUS() {
        this.bySetupAlarmStatus = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byAlarmInStatus = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byAlarmOutStatus = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byBypassStatus = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.bySubSystemGuardStatus = new byte[32];
        this.byAlarmInFaultStatus = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byRes = new byte[56];
    }
}
