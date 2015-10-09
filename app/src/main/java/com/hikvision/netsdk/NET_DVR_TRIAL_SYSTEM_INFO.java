package com.hikvision.netsdk;

public class NET_DVR_TRIAL_SYSTEM_INFO extends NET_DVR_CONFIG {
    public byte byVideoInTypeNum;
    public NET_DVR_VIDEOIN_TYPE_INFO[] struVideoIn;

    public NET_DVR_TRIAL_SYSTEM_INFO() {
        this.struVideoIn = new NET_DVR_VIDEOIN_TYPE_INFO[10];
        for (int i = 0; i < 10; i++) {
            this.struVideoIn[i] = new NET_DVR_VIDEOIN_TYPE_INFO();
        }
    }
}
