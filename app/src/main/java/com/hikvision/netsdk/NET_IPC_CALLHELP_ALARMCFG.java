package com.hikvision.netsdk;

public class NET_IPC_CALLHELP_ALARMCFG {
    public byte byAlarmHandle;
    public byte[] byRelRecordChan;
    public NET_DVR_HANDLEEXCEPTION_V30 struAlarmHandleType;

    public NET_IPC_CALLHELP_ALARMCFG() {
        this.struAlarmHandleType = new NET_DVR_HANDLEEXCEPTION_V30();
        this.byRelRecordChan = new byte[64];
    }
}
