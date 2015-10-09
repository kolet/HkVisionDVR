package com.hikvision.netsdk;

public class NET_DVR_NETCFG_V30 extends NET_DVR_CONFIG {
    public byte[] byIpResolver;
    public byte byUseDhcp;
    public NET_DVR_IPADDR struAlarmHostIpAddr;
    public NET_DVR_IPADDR struDnsServer1IpAddr;
    public NET_DVR_IPADDR struDnsServer2IpAddr;
    public NET_DVR_ETHERNET_V30[] struEtherNet;
    public NET_DVR_IPADDR struGatewayIpAddr;
    public NET_DVR_IPADDR struMulticastIpAddr;
    public NET_DVR_PPPOECFG struPPPoE;
    public int wAlarmHostIpPort;
    public int wHttpPortNo;
    public int wIpResolverPort;

    public NET_DVR_NETCFG_V30() {
        this.struEtherNet = new NET_DVR_ETHERNET_V30[2];
        this.struAlarmHostIpAddr = new NET_DVR_IPADDR();
        this.struDnsServer1IpAddr = new NET_DVR_IPADDR();
        this.struDnsServer2IpAddr = new NET_DVR_IPADDR();
        this.byIpResolver = new byte[64];
        this.struMulticastIpAddr = new NET_DVR_IPADDR();
        this.struGatewayIpAddr = new NET_DVR_IPADDR();
        this.struPPPoE = new NET_DVR_PPPOECFG();
        for (int i = 0; i < 2; i++) {
            this.struEtherNet[i] = new NET_DVR_ETHERNET_V30();
        }
    }
}
