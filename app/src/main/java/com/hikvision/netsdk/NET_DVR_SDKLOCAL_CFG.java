package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_SDKLOCAL_CFG {
    public byte byCompatibleType;
    public byte byEnableAbilityParse;
    public byte[] byProtectKey;

    public NET_DVR_SDKLOCAL_CFG() {
        this.byProtectKey = new byte[Constants.SUPPORT_SSE];
    }
}
