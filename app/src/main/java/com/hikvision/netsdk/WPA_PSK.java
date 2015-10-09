package com.hikvision.netsdk;

public class WPA_PSK {
    public byte byEncryptType;
    public int dwKeyLength;
    public byte[] sKeyInfo;

    public WPA_PSK() {
        this.sKeyInfo = new byte[63];
    }
}
