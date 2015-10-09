package com.example.andrei.cctv.hikvision;

public class DvrDeviceInfo {
    public String serverip = "";
    public int serverport = 8000;
    public String username = "";
    public String userpwd = "";
    public String describe = "";

    public String serialNumber;

    // The number of analog channels
    public byte channelNumber;

    // Starting number of analog channel, starts from 1
    public byte startChannel = 1;
}
