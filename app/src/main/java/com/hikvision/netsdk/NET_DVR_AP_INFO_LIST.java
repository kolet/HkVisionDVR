package com.hikvision.netsdk;

public class NET_DVR_AP_INFO_LIST extends NET_DVR_CONFIG {
    public int dwCount;
    public NET_DVR_AP_INFO[] struApInfo;

    public NET_DVR_AP_INFO_LIST() {
        this.struApInfo = new NET_DVR_AP_INFO[20];
        for (int i = 0; i < 20; i++) {
            this.struApInfo[i] = new NET_DVR_AP_INFO();
        }
    }
}
