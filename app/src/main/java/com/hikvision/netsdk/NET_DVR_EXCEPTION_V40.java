package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_EXCEPTION_V40 extends NET_DVR_CONFIG {
    public byte[] byRes;
    public int dwMaxGroupNum;
    public NET_DVR_HANDLEEXCEPTION_V41[] struExceptionHandle;

    public NET_DVR_EXCEPTION_V40() {
        this.struExceptionHandle = new NET_DVR_HANDLEEXCEPTION_V41[32];
        this.byRes = new byte[Constants.SUPPORT_SSE];
        for (int i = 0; i < 32; i++) {
            this.struExceptionHandle[i] = new NET_DVR_HANDLEEXCEPTION_V41();
        }
    }
}
