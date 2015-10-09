package com.hikvision.netsdk;

public class EAP_TLS {
    public byte byEapolVersion;
    public byte[] byIdentity;
    public byte[] byPrivateKeyPswd;

    public EAP_TLS() {
        this.byIdentity = new byte[32];
        this.byPrivateKeyPswd = new byte[32];
    }
}
