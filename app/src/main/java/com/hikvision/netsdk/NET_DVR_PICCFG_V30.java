package com.hikvision.netsdk;

public class NET_DVR_PICCFG_V30 extends NET_DVR_CONFIG {
    public byte byDispWeek;
    public byte byFontSize;
    public byte byHourOsdType;
    public byte byOSDAttrib;
    public byte byOSDType;
    public int dwEnableHide;
    public int dwShowChanName;
    public int dwShowOsd;
    public int dwVideoFormat;
    public byte[] sChanName;
    public NET_DVR_HIDEALARM_V30 struHideAlarm;
    public NET_DVR_MOTION_V30 struMotion;
    public NET_DVR_SHELTER[] struShelter;
    public NET_DVR_VILOST_V30 struVILost;
    public NET_DVR_VICOLOR struViColor;
    public short wOSDTopLeftX;
    public short wOSDTopLeftY;
    public short wShowNameTopLeftX;
    public short wShowNameTopLeftY;

    public NET_DVR_PICCFG_V30() {
        this.sChanName = new byte[32];
        this.struViColor = new NET_DVR_VICOLOR();
        this.struVILost = new NET_DVR_VILOST_V30();
        this.struMotion = new NET_DVR_MOTION_V30();
        this.struHideAlarm = new NET_DVR_HIDEALARM_V30();
        this.struShelter = new NET_DVR_SHELTER[4];
        for (int i = 0; i < 4; i++) {
            this.struShelter[i] = new NET_DVR_SHELTER();
        }
    }
}
