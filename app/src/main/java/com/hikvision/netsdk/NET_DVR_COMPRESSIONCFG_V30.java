package com.hikvision.netsdk;

public class NET_DVR_COMPRESSIONCFG_V30 extends NET_DVR_CONFIG {
    public NET_DVR_COMPRESSION_INFO_V30 struEventRecordPara;
    public NET_DVR_COMPRESSION_INFO_V30 struNetPara;
    public NET_DVR_COMPRESSION_INFO_V30 struNormHighRecordPara;
    public NET_DVR_COMPRESSION_INFO_V30 struRes;

    public NET_DVR_COMPRESSIONCFG_V30() {
        this.struNormHighRecordPara = new NET_DVR_COMPRESSION_INFO_V30();
        this.struEventRecordPara = new NET_DVR_COMPRESSION_INFO_V30();
        this.struNetPara = new NET_DVR_COMPRESSION_INFO_V30();
        this.struRes = new NET_DVR_COMPRESSION_INFO_V30();
    }
}
