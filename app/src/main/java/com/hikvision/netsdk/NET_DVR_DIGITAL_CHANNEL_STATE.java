package com.hikvision.netsdk;

public class NET_DVR_DIGITAL_CHANNEL_STATE extends NET_DVR_CONFIG {
    public byte[] byAnalogChanState;
    public byte[] byDigitalAudioChanTalkState;
    public byte[] byDigitalAudioChanTalkStateEx;
    public byte[] byDigitalChanState;
    public byte[] byDigitalChanStateEx;
    public byte[] byRes;

    public NET_DVR_DIGITAL_CHANNEL_STATE() {
        this.byDigitalAudioChanTalkState = new byte[64];
        this.byDigitalChanState = new byte[64];
        this.byDigitalAudioChanTalkStateEx = new byte[NET_DVR_LOG_TYPE.MINOR_GET_REMOTE_REPLAY];
        this.byDigitalChanStateEx = new byte[NET_DVR_LOG_TYPE.MINOR_GET_REMOTE_REPLAY];
        this.byAnalogChanState = new byte[32];
        this.byRes = new byte[32];
    }
}
