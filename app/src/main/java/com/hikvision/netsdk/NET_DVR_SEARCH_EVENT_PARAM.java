package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_SEARCH_EVENT_PARAM {
    public byte byLockType;
    public int[] dwVCAChanNo;
    public NET_DVR_TIME struEndTime;
    public NET_DVR_TIME struStartTime;
    public int[] wAlarmInNo;
    public int[] wBehaviorChanNo;
    public short wMajorType;
    public short wMinorType;
    public int[] wMotDetChanNo;

    public NET_DVR_SEARCH_EVENT_PARAM() {
        this.struStartTime = new NET_DVR_TIME();
        this.struEndTime = new NET_DVR_TIME();
        this.wAlarmInNo = new int[Constants.SUPPORT_SSE];
        this.wMotDetChanNo = new int[64];
        this.wBehaviorChanNo = new int[64];
        this.dwVCAChanNo = new int[63];
    }
}
