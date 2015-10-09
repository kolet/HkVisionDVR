package com.hikvision.netsdk;

public class NET_DVR_SERVER_DEVICE_INFO extends NET_DVR_CONFIG {
    public byte[] byRes;
    public int dwDeviceNum;
    public NET_DVR_SERVER_DEVICE_CFG[] struDeviceCfg;

    public NET_DVR_SERVER_DEVICE_INFO() {
        this.struDeviceCfg = new NET_DVR_SERVER_DEVICE_CFG[16];
        this.byRes = new byte[NET_DVR_LOG_TYPE.MINOR_UPLOAD_LOGO];
        for (int i = 0; i < 16; i++) {
            this.struDeviceCfg[i] = new NET_DVR_SERVER_DEVICE_CFG();
        }
    }
}
