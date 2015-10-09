package com.hikvision.netsdk;

public class NET_DVR_SHOWSTRING_V30 extends NET_DVR_CONFIG {
    public NET_DVR_SHOWSTRINGINFO[] struStringInfo;

    public NET_DVR_SHOWSTRING_V30() {
        this.struStringInfo = new NET_DVR_SHOWSTRINGINFO[8];
        for (int i = 0; i < 8; i++) {
            this.struStringInfo[i] = new NET_DVR_SHOWSTRINGINFO();
        }
    }
}
