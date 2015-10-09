package com.hikvision.netsdk;

public class EAP_TTLS {
    public byte[] byAnonyIdentity;
    public byte byAuthType;
    public byte byEapolVersion;
    public byte[] byPassword;
    public byte[] byUserName;

    public EAP_TTLS() {
        this.byAnonyIdentity = new byte[32];
        this.byUserName = new byte[32];
        this.byPassword = new byte[32];
    }
}
