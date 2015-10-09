package com.hikvision.netsdk;

public class NET_DVR_PREVIEW_SWITCH_CFG extends NET_DVR_CONFIG {
    public byte byEnableAudio;
    public byte byPreviewNumber;
    public byte bySameSource;
    public byte bySwitchTime;
    public short[] wSwitchSeq;

    public NET_DVR_PREVIEW_SWITCH_CFG() {
        this.wSwitchSeq = new short[64];
    }
}
