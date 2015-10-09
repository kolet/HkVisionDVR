package com.hikvision.netsdk;

public class NET_DVR_DEVICEINFO_V30 {
    public byte byAlarmInPortNum;
    public byte byAlarmOutPortNum;
    public byte byAudioChanNum;
    public byte byChanNum;
    public byte byDVRType;
    public byte byDiskNum;
    public byte byHighDChanNum;
    public short byIPChanNum;
    public byte byStartChan;
    public byte byStartDChan;
    public byte byStartDTalkChan;
    public byte byZeroChanNum;
    public byte[] sSerialNumber;
    public short wDevType;

    public NET_DVR_DEVICEINFO_V30() {
        this.sSerialNumber = new byte[48];
    }
}
