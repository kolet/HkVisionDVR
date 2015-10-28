package com.example.andrei.cctv.hikvision;

public class DvrDeviceInfo {
    public String serverIP = "";
    public int serverPort = 8000;
    public String userName = "";
    public String userPassword = "";
    public String description = "";

    public String serialNumber;

    // The number of analog channels
    public byte channelNumber;

    // Starting number of analog channel, starts from 1
    public byte startChannel = 1;
}
