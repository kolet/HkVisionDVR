package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_MOTION_V30 {
    public byte byEnableHandleMotion;
    public byte[][] byMotionScope;
    public byte byMotionSensitive;
    public byte[] byRelRecordChan;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;
    public NET_DVR_HANDLEEXCEPTION_V30 struMotionHandleType;

    public NET_DVR_MOTION_V30() {
        this.byMotionScope = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{64, 96});
        this.struMotionHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        this.byRelRecordChan = new byte[64];
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
