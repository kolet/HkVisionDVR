package com.hikvision.netsdk;

public class NET_DVR_IPPARACFG_V31 extends NET_DVR_CONFIG {
    public byte[] byAnalogChanEnable;
    public NET_DVR_IPCHANINFO[] struIPChanInfo;
    public NET_DVR_IPDEVINFO_V31[] struIPDevInfo;

    public NET_DVR_IPPARACFG_V31() {
        int i = 0;
        this.struIPDevInfo = new NET_DVR_IPDEVINFO_V31[32];
        this.byAnalogChanEnable = new byte[32];
        this.struIPChanInfo = new NET_DVR_IPCHANINFO[32];
        for (int i2 = 0; i2 < 32; i2++) {
            this.struIPDevInfo[i2] = new NET_DVR_IPDEVINFO_V31();
        }
        while (i < 32) {
            this.struIPChanInfo[i] = new NET_DVR_IPCHANINFO();
            i++;
        }
    }
}
