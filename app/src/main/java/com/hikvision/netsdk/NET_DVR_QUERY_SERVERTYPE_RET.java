package com.hikvision.netsdk;

public class NET_DVR_QUERY_SERVERTYPE_RET extends NET_DVR_ADDR_QUERY_RET {
    public byte[] szSvrAddr;
    public int wSvrPort;

    public NET_DVR_QUERY_SERVERTYPE_RET() {
        this.szSvrAddr = new byte[64];
    }
}
