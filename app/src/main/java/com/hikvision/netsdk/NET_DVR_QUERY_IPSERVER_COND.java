package com.hikvision.netsdk;

public class NET_DVR_QUERY_IPSERVER_COND extends NET_DVR_ADDR_QUERY_COND {
    public byte[] szDevNickName;
    public byte[] szDevSerial;
    public byte[] szResolveSvrAddr;
    public int wResolveSvrPort;

    public NET_DVR_QUERY_IPSERVER_COND() {
        this.szResolveSvrAddr = new byte[64];
        this.szDevNickName = new byte[64];
        this.szDevSerial = new byte[48];
    }
}
