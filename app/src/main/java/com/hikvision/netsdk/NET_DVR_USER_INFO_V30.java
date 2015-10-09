package com.hikvision.netsdk;

public class NET_DVR_USER_INFO_V30 {
    public byte[] byLocalBackupRight;
    public byte[] byLocalPTZRight;
    public byte[] byLocalPlaybackRight;
    public byte[] byLocalRecordRight;
    public byte[] byLocalRight;
    public byte[] byMACAddr;
    public byte[] byNetPTZRight;
    public byte[] byNetPlaybackRight;
    public byte[] byNetPreviewRight;
    public byte[] byNetRecordRight;
    public byte byPriority;
    public byte[] byRemoteRight;
    public byte[] byRes;
    public byte[] sPassword;
    public byte[] sUserName;
    public NET_DVR_IPADDR struUserIP;

    public NET_DVR_USER_INFO_V30() {
        this.sUserName = new byte[32];
        this.sPassword = new byte[16];
        this.byLocalRight = new byte[32];
        this.byRemoteRight = new byte[32];
        this.byNetPreviewRight = new byte[64];
        this.byLocalPlaybackRight = new byte[64];
        this.byNetPlaybackRight = new byte[64];
        this.byLocalRecordRight = new byte[64];
        this.byNetRecordRight = new byte[64];
        this.byLocalPTZRight = new byte[64];
        this.byNetPTZRight = new byte[64];
        this.byLocalBackupRight = new byte[64];
        this.struUserIP = new NET_DVR_IPADDR();
        this.byMACAddr = new byte[6];
        this.byRes = new byte[17];
    }
}
