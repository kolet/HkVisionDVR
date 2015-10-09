package com.hikvision.netsdk;

public class NET_DVR_CONTROL_GATEWAY extends NET_DVR_CONTROL {
    public byte byCommand;
    public byte[] byControlSrc;
    public byte byControlType;
    public byte[] byRes1;
    public byte[] byRes2;
    public int dwGatewayIndex;

    public NET_DVR_CONTROL_GATEWAY() {
        this.byRes1 = new byte[3];
        this.byControlSrc = new byte[32];
        this.byRes2 = new byte[NET_DVR_LOG_TYPE.MINOR_REMOTE_PLAYBYFILE];
    }
}
