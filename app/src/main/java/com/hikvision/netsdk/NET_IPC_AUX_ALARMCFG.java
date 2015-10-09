package com.hikvision.netsdk;

public class NET_IPC_AUX_ALARMCFG extends NET_DVR_CONFIG {
    public NET_IPC_SINGLE_AUX_ALARMCFG[] struAlarm;

    public NET_IPC_AUX_ALARMCFG() {
        this.struAlarm = new NET_IPC_SINGLE_AUX_ALARMCFG[8];
        for (int i = 0; i < 8; i++) {
            this.struAlarm[i] = new NET_IPC_SINGLE_AUX_ALARMCFG();
        }
    }
}
