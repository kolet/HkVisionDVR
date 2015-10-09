package com.hikvision.netsdk;

public class NET_DVR_USER_V30 extends NET_DVR_CONFIG {
    public NET_DVR_USER_INFO_V30[] struUser;

    public NET_DVR_USER_V30() {
        this.struUser = new NET_DVR_USER_INFO_V30[32];
        for (int i = 0; i < 32; i++) {
            this.struUser[i] = new NET_DVR_USER_INFO_V30();
        }
    }
}
