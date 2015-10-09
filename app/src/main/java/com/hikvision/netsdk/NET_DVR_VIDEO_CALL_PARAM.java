package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_VIDEO_CALL_PARAM extends NET_DVR_CONFIG {
    public byte[] byRes;
    public int dwCmdType;

    public NET_DVR_VIDEO_CALL_PARAM() {
        this.byRes = new byte[Constants.SUPPORT_SSE];
    }
}
