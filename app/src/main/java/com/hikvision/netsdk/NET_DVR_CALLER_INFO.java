package com.hikvision.netsdk;

public class NET_DVR_CALLER_INFO extends NET_DVR_CONFIG {
    public byte byDevNo;
    public byte byDevType;
    public byte[] byRes;
    public byte byUnitNo;
    public byte byZoneNo;
    public int wBuildingNo;
    public int wFloorNo;

    public NET_DVR_CALLER_INFO() {
        this.byRes = new byte[100];
    }
}
