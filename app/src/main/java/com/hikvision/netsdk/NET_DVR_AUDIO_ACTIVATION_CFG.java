package com.hikvision.netsdk;

public class NET_DVR_AUDIO_ACTIVATION_CFG extends NET_DVR_CONFIG {
    public byte byEnable;
    public byte byEnablePreset;
    public byte byPriority;
    public byte bySensitivity;
    public byte[] byVoChanNo;
    public int dwChanNo;
    public int wBase;
    public int wDelayTime;
    public int wPreset;

    public NET_DVR_AUDIO_ACTIVATION_CFG() {
        this.byVoChanNo = new byte[9];
    }
}
