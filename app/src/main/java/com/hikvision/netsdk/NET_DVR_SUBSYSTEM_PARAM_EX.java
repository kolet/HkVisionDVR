package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_SUBSYSTEM_PARAM_EX extends NET_DVR_CONFIG {
    public byte byAlarmInAdvance;
    public byte[] byJointAlarmIn;
    public byte[] byJointKeyboard;
    public byte[] byJointOpetaterUser;
    public byte[] byRes1;
    public byte[] byRes2;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;

    public NET_DVR_SUBSYSTEM_PARAM_EX() {
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        this.byRes1 = new byte[3];
        this.byJointAlarmIn = new byte[64];
        this.byJointKeyboard = new byte[8];
        this.byJointOpetaterUser = new byte[32];
        this.byRes2 = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
