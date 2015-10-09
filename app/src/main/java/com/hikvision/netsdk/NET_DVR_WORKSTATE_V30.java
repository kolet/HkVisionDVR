package com.hikvision.netsdk;

public class NET_DVR_WORKSTATE_V30 {
    public byte[] byAlarmInStatic;
    public byte[] byAlarmOutStatic;
    public byte[] byAudioChanStatus;
    public int dwDeviceStatic;
    public int dwLocalDisplay;
    public NET_DVR_CHANNELSTATE_V30[] struChanStatic;
    public NET_DVR_DISKSTATE[] struHardDiskStatic;

    public NET_DVR_WORKSTATE_V30() {
        int i = 0;
        this.struHardDiskStatic = new NET_DVR_DISKSTATE[33];
        this.struChanStatic = new NET_DVR_CHANNELSTATE_V30[64];
        this.byAlarmInStatic = new byte[NET_DVR_LOG_TYPE.MINOR_SUBSYSTEMREBOOT];
        this.byAlarmOutStatic = new byte[96];
        this.byAudioChanStatus = new byte[2];
        for (int i2 = 0; i2 < 33; i2++) {
            this.struHardDiskStatic[i2] = new NET_DVR_DISKSTATE();
        }
        while (i < 64) {
            this.struChanStatic[i] = new NET_DVR_CHANNELSTATE_V30();
            i++;
        }
    }
}
