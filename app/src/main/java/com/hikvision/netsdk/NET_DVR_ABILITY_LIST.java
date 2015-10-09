package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_ABILITY_LIST {
    public byte[] byRes;
    public int dwAbilityType;
    public int dwNodeNum;
    public NET_DVR_DESC_NODE[] struDescNode;

    public NET_DVR_ABILITY_LIST() {
        this.byRes = new byte[32];
        this.struDescNode = new NET_DVR_DESC_NODE[Constants.VIDEO_AVC264];
        for (int i = 0; i < Constants.VIDEO_AVC264; i++) {
            this.struDescNode[i] = new NET_DVR_DESC_NODE();
        }
    }
}
