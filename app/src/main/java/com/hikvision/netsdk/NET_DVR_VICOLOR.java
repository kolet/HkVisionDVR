package com.hikvision.netsdk;

public class NET_DVR_VICOLOR {
    public NET_DVR_COLOR[] struColor;
    public NET_DVR_SCHEDTIME[] struHandleTime;

    public NET_DVR_VICOLOR() {
        this.struColor = new NET_DVR_COLOR[8];
        this.struHandleTime = new NET_DVR_SCHEDTIME[8];
        for (int i = 0; i < 8; i++) {
            this.struColor[i] = new NET_DVR_COLOR();
            this.struHandleTime[i] = new NET_DVR_SCHEDTIME();
        }
    }
}
