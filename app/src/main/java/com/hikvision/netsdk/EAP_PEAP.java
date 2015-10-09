package com.hikvision.netsdk;

public class EAP_PEAP {
    public byte[] byAnonyIdentity;
    public byte byAuthType;
    public byte byEapolVersion;
    public byte[] byPassword;
    public byte byPeapLabel;
    public byte byPeapVersion;
    public byte[] byUserName;

    public EAP_PEAP() {
        this.byAnonyIdentity = new byte[32];
        this.byUserName = new byte[32];
        this.byPassword = new byte[32];
    }
}
