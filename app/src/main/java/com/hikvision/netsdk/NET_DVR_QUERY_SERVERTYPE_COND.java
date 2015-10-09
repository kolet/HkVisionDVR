package com.hikvision.netsdk;

public class NET_DVR_QUERY_SERVERTYPE_COND extends NET_DVR_ADDR_QUERY_COND {
    public byte[] szClientVersion;
    public byte[] szSvrAddr;
    public int wSvrType;

    public NET_DVR_QUERY_SERVERTYPE_COND() {
        this.szSvrAddr = new byte[64];
        this.szClientVersion = new byte[64];
    }
}
