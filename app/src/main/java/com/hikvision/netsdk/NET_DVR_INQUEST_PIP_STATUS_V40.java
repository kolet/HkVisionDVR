package com.hikvision.netsdk;

public class NET_DVR_INQUEST_PIP_STATUS_V40 {
    public byte byBackChan;
    public byte byBaseChan;
    public byte byPIPMode;
    public byte byPicShowMode;
    public byte byPipCount;
    public NET_DVR_INQUEST_PIP_PARAM_V40[] struPipPara;

    public NET_DVR_INQUEST_PIP_STATUS_V40() {
        this.struPipPara = new NET_DVR_INQUEST_PIP_PARAM_V40[16];
        for (int i = 0; i < 16; i++) {
            this.struPipPara[i] = new NET_DVR_INQUEST_PIP_PARAM_V40();
        }
    }
}
