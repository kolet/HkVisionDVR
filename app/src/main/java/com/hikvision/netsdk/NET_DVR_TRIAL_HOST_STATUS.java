package com.hikvision.netsdk;

public class NET_DVR_TRIAL_HOST_STATUS extends NET_DVR_CONFIG {
    public byte[] byFpgaTempWarn;
    public int[] dwFanSpeed;
    public short[] wMainBoardTemp;

    public NET_DVR_TRIAL_HOST_STATUS() {
        this.dwFanSpeed = new int[8];
        this.wMainBoardTemp = new short[8];
        this.byFpgaTempWarn = new byte[8];
    }
}
