package com.hikvision.netsdk;

public class NET_DVR_DESC_NODE {
    public byte[] byDescribe;
    public byte[] byRes;
    public int dwFreeSpace;
    public int iValue;

    public NET_DVR_DESC_NODE() {
        this.byDescribe = new byte[32];
        this.byRes = new byte[12];
    }
}
