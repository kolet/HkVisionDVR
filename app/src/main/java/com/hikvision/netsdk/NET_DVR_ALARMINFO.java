package com.hikvision.netsdk;

public class NET_DVR_ALARMINFO extends NET_DVR_BASE_ALARM {
    public int dwAlarmInputNumber;
    public int[] dwAlarmOutputNumber;
    public int[] dwAlarmRelateChannel;
    public int dwAlarmType;
    public int[] dwChannel;
    public int[] dwDiskNumber;

    public NET_DVR_ALARMINFO() {
        this.dwAlarmOutputNumber = new int[4];
        this.dwAlarmRelateChannel = new int[16];
        this.dwChannel = new int[16];
        this.dwDiskNumber = new int[16];
    }
}
