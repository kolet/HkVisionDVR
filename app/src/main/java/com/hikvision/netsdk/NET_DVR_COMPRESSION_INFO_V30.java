package com.hikvision.netsdk;

public class NET_DVR_COMPRESSION_INFO_V30 extends NET_DVR_PLAYBACK_CONTROL_COND {
    public byte byAudioEncType;
    public byte byBitrateType;
    public byte byENumber;
    public byte byIntervalBPFrame;
    public byte byPicQuality;
    public byte[] byRes;
    public byte byResolution;
    public byte byStreamType;
    public byte byVideoEncType;
    public int dwVideoBitrate;
    public int dwVideoFrameRate;
    public short wIntervalFrameI;

    public NET_DVR_COMPRESSION_INFO_V30() {
        this.byRes = new byte[10];
    }
}
