package com.hikvision.netsdk;

public class NET_DVR_IPALARMOUTCFG extends NET_DVR_CONFIG {
    public NET_DVR_IPALARMOUTINFO[] struIPAlarmOutInfo;

    public NET_DVR_IPALARMOUTCFG() {
        this.struIPAlarmOutInfo = new NET_DVR_IPALARMOUTINFO[64];
        for (int i = 0; i < 64; i++) {
            this.struIPAlarmOutInfo[i] = new NET_DVR_IPALARMOUTINFO();
        }
    }
}
