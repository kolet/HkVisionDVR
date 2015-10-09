package com.hikvision.netsdk;

public class NET_DVR_TRIAL_MICROPHONE_STATUS extends NET_DVR_CONFIG {
    public byte[] byMicrophoneStatus;

    public NET_DVR_TRIAL_MICROPHONE_STATUS() {
        this.byMicrophoneStatus = new byte[16];
    }
}
