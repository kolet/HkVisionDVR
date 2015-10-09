package com.hikvision.netsdk;

public class WPA_WPA2 {
    public byte byAuthType;
    public byte byEncryptType;
    public EAP_PEAP struEapPeap;
    public EAP_TLS struEapTls;
    public EAP_TTLS struEapTtls;

    public WPA_WPA2() {
        this.struEapTtls = new EAP_TTLS();
        this.struEapPeap = new EAP_PEAP();
        this.struEapTls = new EAP_TLS();
    }
}
