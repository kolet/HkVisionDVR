package com.hikvision.netsdk;

public class NET_DVR_PPPOECFG {
    public int dwPPPOE;
    public byte[] sPPPoEPassword;
    public byte[] sPPPoEUser;
    public NET_DVR_IPADDR struPPPoEIP;

    public NET_DVR_PPPOECFG() {
        this.sPPPoEUser = new byte[32];
        this.sPPPoEPassword = new byte[16];
        this.struPPPoEIP = new NET_DVR_IPADDR();
    }
}
