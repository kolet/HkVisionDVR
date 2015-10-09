package com.hikvision.netsdk;

public class NET_DVR_WIFI_CFG extends NET_DVR_CONFIG {
    public int dwMode;
    public int dwSecurity;
    public byte[] sEssid;
    NET_DVR_WIFIETHERNET struEtherNet;
    public WEP wep;
    public WPA_PSK wpa_psk;
    public WPA_WPA2 wpa_wpa2;

    public NET_DVR_WIFI_CFG() {
        this.struEtherNet = new NET_DVR_WIFIETHERNET();
        this.sEssid = new byte[32];
        this.wep = new WEP();
        this.wpa_psk = new WPA_PSK();
        this.wpa_wpa2 = new WPA_WPA2();
    }
}
