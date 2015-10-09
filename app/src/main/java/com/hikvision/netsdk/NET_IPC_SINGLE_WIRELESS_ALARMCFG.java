package com.hikvision.netsdk;

public class NET_IPC_SINGLE_WIRELESS_ALARMCFG {
    public byte byAlarmHandle;
    public byte[] byAlarmName;
    public byte byID;
    public byte[] byRelRecordChan;
    public NET_DVR_HANDLEEXCEPTION_V30 struAlarmHandleType;

    public NET_IPC_SINGLE_WIRELESS_ALARMCFG() {
        this.byAlarmName = new byte[32];
        this.struAlarmHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.byRelRecordChan = new byte[64];
    }
}
