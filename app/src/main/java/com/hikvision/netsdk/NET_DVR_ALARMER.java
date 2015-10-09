package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class NET_DVR_ALARMER {
    public byte byDeviceIPValid;
    public byte byDeviceNameValid;
    public byte byIpProtocol;
    public byte byLinkPortValid;
    public byte[] byMacAddr;
    public byte byMacAddrValid;
    public byte[] byRes2;
    public byte bySerialValid;
    public byte bySocketIPValid;
    public byte byUserIDValid;
    public byte byVersionValid;
    public int dwDeviceVersion;
    public int lUserID;
    public byte[] sDeviceIP;
    public byte[] sDeviceName;
    public byte[] sSerialNumber;
    public byte[] sSocketIP;
    public short wLinkPort;

    public NET_DVR_ALARMER() {
        this.sSerialNumber = new byte[48];
        this.sDeviceName = new byte[32];
        this.byMacAddr = new byte[6];
        this.sDeviceIP = new byte[Constants.SUPPORT_SSE];
        this.sSocketIP = new byte[Constants.SUPPORT_SSE];
        this.byRes2 = new byte[11];
    }
}
