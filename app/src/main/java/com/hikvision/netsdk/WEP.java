package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class WEP {
    public int dwActive;
    public int dwAuthentication;
    public int dwKeyLength;
    public int dwKeyType;
    public byte[][] sKeyInfo;

    public WEP() {
        this.sKeyInfo = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{4, 33});
    }
}
