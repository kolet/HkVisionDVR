package com.hikvision.netsdk;

public class NET_DVR_QUERY_COUNTRYID_RET extends NET_DVR_ADDR_QUERY_RET {
    public byte[] szAlarmSvrAddr;
    public byte[] szResolveSvrAddr;

    public NET_DVR_QUERY_COUNTRYID_RET() {
        this.szResolveSvrAddr = new byte[64];
        this.szAlarmSvrAddr = new byte[64];
    }
}
