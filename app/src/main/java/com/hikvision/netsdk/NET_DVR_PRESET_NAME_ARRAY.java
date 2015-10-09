package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_PRESET_NAME_ARRAY extends NET_DVR_CONFIG {
    public NET_DVR_PRESET_NAME[] struPresetName;

    public NET_DVR_PRESET_NAME_ARRAY() {
        this.struPresetName = new NET_DVR_PRESET_NAME[Constants.VIDEO_AVC264];
        for (int i = 0; i < Constants.VIDEO_AVC264; i++) {
            this.struPresetName[i] = new NET_DVR_PRESET_NAME();
        }
    }
}
