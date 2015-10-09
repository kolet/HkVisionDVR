package com.hikvision.netsdk;

public class NET_DVR_COMPRESSIONCFG_ABILITY {
    public int dwAbilityNum;
    public int dwSize;
    public NET_DVR_ABILITY_LIST[] struAbilityNode;

    public NET_DVR_COMPRESSIONCFG_ABILITY() {
        this.struAbilityNode = new NET_DVR_ABILITY_LIST[12];
        for (int i = 0; i < 12; i++) {
            this.struAbilityNode[i] = new NET_DVR_ABILITY_LIST();
        }
    }
}
