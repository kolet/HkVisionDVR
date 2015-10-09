package com.hikvision.netsdk;

public class NET_DVR_PRESET_NAME {
    public byte[] byName;
    public int wPresetNum;

    public NET_DVR_PRESET_NAME() {
        this.byName = new byte[32];
    }
}
