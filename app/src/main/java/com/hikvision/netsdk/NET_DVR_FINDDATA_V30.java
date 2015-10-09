package com.hikvision.netsdk;

public class NET_DVR_FINDDATA_V30 {
    public byte byFileType;
    public byte byLocked;
    public byte[] byRes;
    public int dwFileSize;
    public byte[] sCardNum;
    public byte[] sFileName;
    public NET_DVR_TIME struStartTime;
    public NET_DVR_TIME struStopTime;

    public NET_DVR_FINDDATA_V30() {
        this.sFileName = new byte[100];
        this.struStartTime = new NET_DVR_TIME();
        this.struStopTime = new NET_DVR_TIME();
        this.sCardNum = new byte[32];
        this.byRes = new byte[2];
    }
}
