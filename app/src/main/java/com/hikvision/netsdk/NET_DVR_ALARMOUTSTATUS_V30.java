package com.hikvision.netsdk;

public class NET_DVR_ALARMOUTSTATUS_V30 extends NET_DVR_CONFIG {
    public byte[] Output;

    public NET_DVR_ALARMOUTSTATUS_V30() {
        this.Output = new byte[96];
    }
}
