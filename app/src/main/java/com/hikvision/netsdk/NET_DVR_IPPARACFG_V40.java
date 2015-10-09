package com.hikvision.netsdk;

public class NET_DVR_IPPARACFG_V40 extends NET_DVR_CONFIG {
    public byte[] byAnalogChanEnable;
    public int dwAChanNum;
    public int dwDChanNum;
    public int dwGroupNum;
    public int dwStartDChan;
    public NET_DVR_IPCHANINFO[] struIPChanInfo;
    public NET_DVR_IPDEVINFO_V31[] struIPDevInfo;

    public NET_DVR_IPPARACFG_V40() {
        int i = 0;
        this.byAnalogChanEnable = new byte[64];
        this.struIPDevInfo = new NET_DVR_IPDEVINFO_V31[64];
        this.struIPChanInfo = new NET_DVR_IPCHANINFO[64];
        for (int i2 = 0; i2 < 64; i2++) {
            this.struIPDevInfo[i2] = new NET_DVR_IPDEVINFO_V31();
        }
        while (i < 64) {
            this.struIPChanInfo[i] = new NET_DVR_IPCHANINFO();
            i++;
        }
    }
}
