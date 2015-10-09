package com.hikvision.netsdk;

public class NET_DVR_COMPRESSION_AUDIO extends NET_DVR_CONFIG {
    public byte byAudioBitRate;
    public byte byAudioEncType;
    public byte byAudioSamplingRate;
    public byte bySupport;
    public byte[] byres;

    public NET_DVR_COMPRESSION_AUDIO() {
        this.byres = new byte[4];
    }
}
