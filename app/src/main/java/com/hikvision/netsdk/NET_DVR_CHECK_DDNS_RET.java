package com.hikvision.netsdk;

public class NET_DVR_CHECK_DDNS_RET extends NET_DVR_ADDR_QUERY_RET {
    public byte byDevStatus;
    public NET_DVR_QUERY_DDNS_RET struQueryRet;
    public int wRegionID;

    public NET_DVR_CHECK_DDNS_RET() {
        this.struQueryRet = new NET_DVR_QUERY_DDNS_RET();
    }
}
