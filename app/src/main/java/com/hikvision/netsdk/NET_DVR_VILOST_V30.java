package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_VILOST_V30 {
    public byte byEnableHandleVILost;
    public NET_DVR_SCHEDTIME[][] struAlarmTime;
    public NET_DVR_HANDLEEXCEPTION_V30 struVILostHandleType;

    public NET_DVR_VILOST_V30() {
        this.struVILostHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.struAlarmTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
