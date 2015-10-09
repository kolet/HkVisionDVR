package com.hikvision.netsdk;

public class NET_IPC_SINGLE_AUX_ALARMCFG {
    public byte byAlarmType;
    public NET_IPC_CALLHELP_ALARMCFG struCallHelpAlarm;
    public NET_IPC_PIR_ALARMCFG struPIRAlarm;
    public NET_IPC_SINGLE_WIRELESS_ALARMCFG[] struWirelessAlarm;

    public NET_IPC_SINGLE_AUX_ALARMCFG() {
        this.struPIRAlarm = new NET_IPC_PIR_ALARMCFG();
        this.struWirelessAlarm = new NET_IPC_SINGLE_WIRELESS_ALARMCFG[8];
        this.struCallHelpAlarm = new NET_IPC_CALLHELP_ALARMCFG();
        for (int i = 0; i < 8; i++) {
            this.struWirelessAlarm[i] = new NET_IPC_SINGLE_WIRELESS_ALARMCFG();
        }
    }
}
