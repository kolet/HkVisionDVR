package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_ALARMOUTCFG_V30 extends NET_DVR_CONFIG {
    public int dwAlarmOutDelay;
    public byte[] sAlarmOutName;
    public NET_DVR_SCHEDTIME[][] struAlarmOutTime;

    public NET_DVR_ALARMOUTCFG_V30() {
        this.sAlarmOutName = new byte[32];
        this.struAlarmOutTime = (NET_DVR_SCHEDTIME[][]) Array.newInstance(NET_DVR_SCHEDTIME.class, new int[]{7, 8});
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 8; i2++) {
                this.struAlarmOutTime[i][i2] = new NET_DVR_SCHEDTIME();
            }
        }
    }
}
