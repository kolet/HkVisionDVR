package com.hikvision.netsdk;

import java.lang.reflect.Array;

public class NET_DVR_RECORD_V30 extends NET_DVR_CONFIG {
    public byte byAudioRec;
    public byte byPassbackRecord;
    public byte byRecordBackup;
    public byte byRedundancyRec;
    public byte[] byReserve;
    public byte bySVCLevel;
    public byte byStreamType;
    public int dwPreRecordTime;
    public int dwRecord;
    public int dwRecordTime;
    public int dwRecorderDuration;
    public NET_DVR_RECORDDAY[] struRecAllDay;
    public NET_DVR_RECORDSCHED[][] struRecordSched;
    public short wLockDuration;

    public NET_DVR_RECORD_V30() {
        this.struRecAllDay = new NET_DVR_RECORDDAY[7];
        this.struRecordSched = (NET_DVR_RECORDSCHED[][]) Array.newInstance(NET_DVR_RECORDSCHED.class, new int[]{7, 8});
        this.byReserve = new byte[4];
        for (int i = 0; i < 7; i++) {
            this.struRecAllDay[i] = new NET_DVR_RECORDDAY();
            for (int i2 = 0; i2 < 8; i2++) {
                this.struRecordSched[i][i2] = new NET_DVR_RECORDSCHED();
            }
        }
    }
}
