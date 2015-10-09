package com.hikvision.netsdk;

public class NET_DVR_QUERY_COUNTRYID_COND extends NET_DVR_ADDR_QUERY_COND {
    public byte[] szClientVersion;
    public byte[] szSvrAddr;
    public int wCountryID;

    public NET_DVR_QUERY_COUNTRYID_COND() {
        this.szSvrAddr = new byte[64];
        this.szClientVersion = new byte[64];
    }
}
