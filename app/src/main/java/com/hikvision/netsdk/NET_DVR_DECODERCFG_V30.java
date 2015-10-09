package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_DECODERCFG_V30 extends NET_DVR_CONFIG {
    public byte byDataBit;
    public byte byFlowcontrol;
    public byte byParity;
    public byte[] bySetCruise;
    public byte[] bySetPreset;
    public byte[] bySetTrack;
    public byte byStopBit;
    public int dwBaudRate;
    public short wDecoderAddress;
    public short wDecoderType;

    public NET_DVR_DECODERCFG_V30() {
        this.bySetPreset = new byte[Constants.VIDEO_AVC264];
        this.bySetCruise = new byte[Constants.VIDEO_AVC264];
        this.bySetTrack = new byte[Constants.VIDEO_AVC264];
    }
}
