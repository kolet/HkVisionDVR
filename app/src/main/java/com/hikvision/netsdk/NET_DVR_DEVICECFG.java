package com.hikvision.netsdk;

public class NET_DVR_DEVICECFG extends NET_DVR_CONFIG {
    public byte byAlarmInPortNum;
    public byte byAlarmOutPortNum;
    public byte byAudioNum;
    public byte byAuxoutNum;
    public byte byChanNum;
    public byte byDVRType;
    public byte byDecordChans;
    public byte byDiskCtrlNum;
    public byte byDiskNum;
    public byte byIPChanNum;
    public byte byNetworkPortNum;
    public byte byRS232Num;
    public byte byRS485Num;
    public byte byStartChan;
    public byte byUSBNum;
    public byte byVGANum;
    public int dwDSPSoftwareBuildDate;
    public int dwDSPSoftwareVersion;
    public int dwDVRID;
    public int dwHardwareVersion;
    public int dwPanelVersion;
    public int dwRecycleRecord;
    public int dwSoftwareBuildDate;
    public int dwSoftwareVersion;
    public byte[] sDVRName;
    public byte[] sSerialNumber;

    public NET_DVR_DEVICECFG() {
        this.sDVRName = new byte[32];
        this.sSerialNumber = new byte[48];
    }
}
