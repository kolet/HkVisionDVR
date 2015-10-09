package com.hikvision.netsdk;

public class NET_DVR_SHOWSTRINGINFO {
    public byte[] sString;
    public int wShowString;
    public int wShowStringTopLeftX;
    public int wShowStringTopLeftY;
    public int wStringSize;

    public NET_DVR_SHOWSTRINGINFO() {
        this.sString = new byte[44];
    }
}
