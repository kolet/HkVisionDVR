package com.hikvision.netsdk;

public class NET_DVR_ALARMSUBSYSTEMPARAM extends NET_DVR_CONFIG {
    public byte byHostageReport;
    public byte byKeyToneOfArmOrDisarm;
    public byte byKeyToneOfManualTestReport;
    public byte byKeyZoneArm;
    public byte byKeyZoneArmReport;
    public byte byKeyZoneDisarm;
    public byte byKeyZoneDisarmReport;
    public byte byPublicAttributeEnable;
    public byte byRes1;
    public byte[] byRes2;
    public byte bySubsystemEnable;
    public NET_DVR_JOINT_SUB_SYSTEM struJointSubSystem;
    public int wDelayTime;
    public int wEnterDelay;
    public int wExitDelay;

    public NET_DVR_ALARMSUBSYSTEMPARAM() {
        this.struJointSubSystem = new NET_DVR_JOINT_SUB_SYSTEM();
        this.byRes2 = new byte[NET_DVR_LOG_TYPE.MINOR_CTRL_SCREEN];
    }
}
