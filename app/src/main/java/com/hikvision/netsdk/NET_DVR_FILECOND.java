package com.hikvision.netsdk;

public class NET_DVR_FILECOND {
    public int dwFileType;
    public int dwIsLocked;
    public int dwUseCardNo;
    public int lChannel;
    public byte[] sCardNumber;
    public NET_DVR_TIME struStartTime;
    public NET_DVR_TIME struStopTime;

    public NET_DVR_FILECOND() {
        this.sCardNumber = new byte[32];
        this.struStartTime = new NET_DVR_TIME();
        this.struStopTime = new NET_DVR_TIME();
    }
}
