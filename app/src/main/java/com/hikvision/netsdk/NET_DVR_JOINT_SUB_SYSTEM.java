package com.hikvision.netsdk;

public class NET_DVR_JOINT_SUB_SYSTEM {
    public byte[] byRes;
    public NET_DVR_NOAMAL_SUB_SYSTEM struNormalSubSystem;
    public NET_DVR_PUBLIC_SUB_SYSTEM struPublicSubSystem;

    public NET_DVR_JOINT_SUB_SYSTEM() {
        this.struNormalSubSystem = new NET_DVR_NOAMAL_SUB_SYSTEM();
        this.struPublicSubSystem = new NET_DVR_PUBLIC_SUB_SYSTEM();
        this.byRes = new byte[20];
    }
}
