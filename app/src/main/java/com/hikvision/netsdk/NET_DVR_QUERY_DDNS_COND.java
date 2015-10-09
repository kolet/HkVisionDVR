package com.hikvision.netsdk;

public class NET_DVR_QUERY_DDNS_COND extends NET_DVR_ADDR_QUERY_COND {
    public byte[] szClientVersion;
    public byte[] szDevNickName;
    public byte[] szDevSerial;
    public byte[] szResolveSvrAddr;

    public NET_DVR_QUERY_DDNS_COND() {
        this.szResolveSvrAddr = new byte[64];
        this.szDevNickName = new byte[64];
        this.szDevSerial = new byte[48];
        this.szClientVersion = new byte[64];
    }
}
