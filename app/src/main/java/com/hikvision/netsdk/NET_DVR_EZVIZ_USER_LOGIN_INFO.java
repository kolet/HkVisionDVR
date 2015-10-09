package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_EZVIZ_USER_LOGIN_INFO {
    public byte[] byRes1;
    public byte[] byRes2;
    public byte[] sClassSession;
    public byte[] sDeviceID;
    public byte[] sEzvizServerAddress;
    public int wPort;

    public NET_DVR_EZVIZ_USER_LOGIN_INFO() {
        this.sEzvizServerAddress = new byte[NET_DVR_LOG_TYPE.MINOR_REMOTE_PTZCTRL];
        this.byRes1 = new byte[2];
        this.sClassSession = new byte[64];
        this.sDeviceID = new byte[32];
        this.byRes2 = new byte[Constants.SUPPORT_SSE];
    }
}
