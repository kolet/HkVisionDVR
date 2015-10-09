package com.hikvision.netsdk;

public class NET_DVR_QUERY_IPSERVER_RET extends NET_DVR_ADDR_QUERY_RET {
    public byte[] szDevIP;
    public int wCmdPort;

    public NET_DVR_QUERY_IPSERVER_RET() {
        this.szDevIP = new byte[48];
    }
}
