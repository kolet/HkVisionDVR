package com.hikvision.netsdk;

public class NET_DVR_PTZCFG {
    public int dwPtzNum;
    public NET_DVR_PTZ_PROTOCOL[] struPtz;

    public NET_DVR_PTZCFG() {
        this.struPtz = new NET_DVR_PTZ_PROTOCOL[NET_DVR_LOG_TYPE.MINOR_UPLOAD_LOGO];
        for (int i = 0; i < NET_DVR_LOG_TYPE.MINOR_UPLOAD_LOGO; i++) {
            this.struPtz[i] = new NET_DVR_PTZ_PROTOCOL();
        }
    }
}
