package com.hikvision.netsdk;

public class NET_DVR_LOG_V30 {
    public int dwAlarmInPort;
    public int dwAlarmOutPort;
    public int dwChannel;
    public int dwDiskNumber;
    public int dwInfoLen;
    public int dwMajorType;
    public int dwMinorType;
    public int dwParaType;
    public byte[] sInfo;
    public byte[] sNetUser;
    public byte[] sPanelUser;
    public NET_DVR_TIME strLogTime;
    public NET_DVR_IPADDR struRemoteHostAddr;

    public NET_DVR_LOG_V30() {
        this.strLogTime = new NET_DVR_TIME();
        this.sPanelUser = new byte[16];
        this.sNetUser = new byte[16];
        this.struRemoteHostAddr = new NET_DVR_IPADDR();
        this.sInfo = new byte[HCNetSDK.LOG_INFO_LEN];
    }
}
