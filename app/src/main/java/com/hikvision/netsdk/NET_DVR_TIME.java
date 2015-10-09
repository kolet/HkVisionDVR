package com.hikvision.netsdk;

public class NET_DVR_TIME extends NET_DVR_CONFIG {
    public int dwDay;
    public int dwHour;
    public int dwMinute;
    public int dwMonth;
    public int dwSecond;
    public int dwYear;

    public String ToString() {
        return this.dwYear + "/" + this.dwMonth + "/" + this.dwDay + " " + this.dwHour + ":" + this.dwMinute + ":" + this.dwSecond;
    }
}
