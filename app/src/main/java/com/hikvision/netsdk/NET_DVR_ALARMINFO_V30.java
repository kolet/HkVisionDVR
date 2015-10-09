package com.hikvision.netsdk;

public class NET_DVR_ALARMINFO_V30 extends NET_DVR_BASE_ALARM {
    public byte[] byAlarmOutputNumber;
    public byte[] byAlarmRelateChannel;
    public byte[] byChannel;
    public byte[] byDiskNumber;
    public int dwAlarmInputNumber;
    public int dwAlarmType;

    public NET_DVR_ALARMINFO_V30() {
        this.byAlarmOutputNumber = new byte[96];
        this.byAlarmRelateChannel = new byte[64];
        this.byChannel = new byte[64];
        this.byDiskNumber = new byte[33];
    }
}
