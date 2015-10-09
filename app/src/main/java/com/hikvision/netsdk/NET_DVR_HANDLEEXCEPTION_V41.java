package com.hikvision.netsdk;

public class NET_DVR_HANDLEEXCEPTION_V41 {
    public byte[] byRes;
    public int dwHandleType;
    public int dwMaxRelAlarmOutChanNum;
    public int[] dwRelAlarmOut;

    public NET_DVR_HANDLEEXCEPTION_V41() {
        this.dwRelAlarmOut = new int[HCNetSDK.MAX_ALARMOUT_V40];
        this.byRes = new byte[64];
    }
}
