package com.hikvision.netsdk;

public class NET_DVR_CALL_STATUS extends NET_DVR_STATUS {
    public byte byCallStatus;
    public byte[] byRes;

    public NET_DVR_CALL_STATUS() {
        this.byRes = new byte[NET_DVR_LOG_TYPE.MINOR_REMOTE_PLAYBYFILE];
    }
}
