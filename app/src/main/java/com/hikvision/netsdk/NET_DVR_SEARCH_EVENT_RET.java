package com.hikvision.netsdk;

public class NET_DVR_SEARCH_EVENT_RET {
    public int dwAlarmInNo;
    public int dwBehaviorChanNo;
    public int dwMotDetNo;
    public NET_DVR_TIME struEndTime;
    public NET_DVR_TIME struStartTime;
    public short wMajorType;
    public short wMinorType;

    public NET_DVR_SEARCH_EVENT_RET() {
        this.struStartTime = new NET_DVR_TIME();
        this.struEndTime = new NET_DVR_TIME();
    }
}
