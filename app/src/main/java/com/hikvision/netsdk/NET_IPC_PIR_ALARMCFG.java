package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_IPC_PIR_ALARMCFG {
    public byte byAlarmHandle;
    public byte[] byAlarmName;
    public byte[] byRelRecordChan;
    public NET_DVR_HANDLEEXCEPTION_V30 struAlarmHandleType;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;

    public NET_IPC_PIR_ALARMCFG() {
        this.byAlarmName = new byte[32];
        this.struAlarmHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.byRelRecordChan = new byte[64];
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
