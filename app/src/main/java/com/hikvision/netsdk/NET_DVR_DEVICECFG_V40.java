package com.hikvision.netsdk;

public class NET_DVR_DEVICECFG_V40 extends NET_DVR_CONFIG {
    public byte[] byDevTypeName;

    public NET_DVR_DEVICECFG_V40() {
        this.byDevTypeName = new byte[24];
    }
}
