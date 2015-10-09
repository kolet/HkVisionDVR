package com.hikvision.netsdk;

public class NET_DVR_RECORDSCHED {
    public byte byRecordType;
    public NET_DVR_SCHEDTIME struRecordTime;

    public NET_DVR_RECORDSCHED() {
        this.struRecordTime = new NET_DVR_SCHEDTIME();
    }
}
