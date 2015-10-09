package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_ALARMIN_PARAM extends NET_DVR_CONFIG {
    public byte byAlarmType;
    public byte byArrayBypass;
    public byte[] byAssociateAlarmOut;
    public byte[] byAssociateSirenOut;
    public byte byJointSubSystem;
    public byte byModuleChan;
    public byte byModuleStatus;
    public byte byModuleType;
    public byte[] byName;
    public byte[] byRes2;
    public byte bySensitivityParam;
    public byte byType;
    public byte byUploadAlarmRecoveryReport;
    public int dwParam;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;
    public int wDetectorType;
    public int wInDelay;
    public int wModuleAddress;
    public int wOutDelay;
    public int wZoneIndex;

    public NET_DVR_ALARMIN_PARAM() {
        this.byName = new byte[32];
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 4});
        this.byAssociateAlarmOut = new byte[HCNetSDK.MAX_ALARMHOST_ALARMOUT_NUM];
        this.byAssociateSirenOut = new byte[8];
        this.byRes2 = new byte[37];
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
