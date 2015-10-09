package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_ALARMINCFG_V30 extends NET_DVR_CONFIG {
    public byte byAlarmInHandle;
    public byte byAlarmType;
    public byte byChannel;
    public byte[] byCruiseNo;
    public byte[] byEnableCruise;
    public byte[] byEnablePreset;
    public byte[] byEnablePtzTrack;
    public byte[] byPTZTrack;
    public byte[] byPresetNo;
    public byte[] byRelRecordChan;
    public byte[] sAlarmInName;
    public NET_DVR_HANDLEEXCEPTION_V30 struAlarmHandleType;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;

    public NET_DVR_ALARMINCFG_V30() {
        this.sAlarmInName = new byte[32];
        this.struAlarmHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        this.byRelRecordChan = new byte[64];
        this.byEnablePreset = new byte[64];
        this.byPresetNo = new byte[64];
        this.byEnableCruise = new byte[64];
        this.byCruiseNo = new byte[64];
        this.byEnablePtzTrack = new byte[64];
        this.byPTZTrack = new byte[64];
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
