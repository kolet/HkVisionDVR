package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_ZONE_CHANNEL_LINKAGE_CFG extends NET_DVR_CONFIG {
    public byte[] byRes;
    public NET_DVR_SINGLE_CHANNEL_LINKAGE_CFG[] struLinkChannels;

    public NET_DVR_ZONE_CHANNEL_LINKAGE_CFG() {
        this.struLinkChannels = new NET_DVR_SINGLE_CHANNEL_LINKAGE_CFG[4];
        this.byRes = new byte[Constants.VIDEO_AVC264];
        for (int i = 0; i < 4; i++) {
            this.struLinkChannels[i] = new NET_DVR_SINGLE_CHANNEL_LINKAGE_CFG();
        }
    }
}
