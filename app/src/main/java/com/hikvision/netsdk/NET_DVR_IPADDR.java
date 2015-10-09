package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_IPADDR {
    public byte[] sIpV4;
    public byte[] sIpV6;

    public NET_DVR_IPADDR() {
        this.sIpV4 = new byte[16];
        this.sIpV6 = new byte[Constants.SUPPORT_SSE];
    }
}
