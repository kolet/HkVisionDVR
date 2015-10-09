package com.hikvision.netsdk;

import org.MediaPlayer.PlayM4.Constants;

public class HCNetSDK {
    public static final int ALARMHOST_MAX_AUDIOOUT_NUM = 32;
    public static final int ALARMHOST_MAX_ELECTROLOCK_NUM = 32;
    public static final int ALARMHOST_MAX_MOBILEGATE_NUM = 32;
    public static final int ALARMHOST_MAX_SIREN_NUM = 8;
    public static final int ALARM_RECONNECTSUCCESS = 32790;
    public static final int CALLER_SERIAL_NUMBER = 11;
    public static final int CLIENT_VERSION_LEN = 64;
    public static final int CODECARD_ABILITY = 625;
    public static final int COMM_ALARM = 4352;
    public static final int COMM_ALARM_V30 = 16384;
    public static final int COMPRESSIONCFG_ABILITY = 1024;
    public static final int COMPRESSION_LIMIT = 1025;
    public static final int DECODER_ABILITY = 609;
    public static final int DESC_LEN = 16;
    public static final int DESC_LEN_32 = 32;
    public static final int DEVICE_ABILITY_INFO = 17;
    public static final int DEVICE_ALARM_ABILITY = 10;
    public static final int DEVICE_DYNCHAN_ABILITY = 11;
    public static final int DEVICE_ENCODE_ALL_ABILITY = 3;
    public static final int DEVICE_ENCODE_ALL_ABILITY_V20 = 8;
    public static final int DEVICE_ENCODE_CURRENT = 4;
    public static final int DEVICE_JPEG_CAP_ABILITY = 15;
    public static final int DEVICE_NETAPP_ABILITY = 13;
    public static final int DEVICE_NETWORK_ABILITY = 2;
    public static final int DEVICE_RAID_ABILITY = 7;
    public static final int DEVICE_SERIAL_ABILITY = 16;
    public static final int DEVICE_SOFTHARDWARE_ABILITY = 1;
    public static final int DEVICE_USER_ABILITY = 12;
    public static final int DEVICE_VIDEOPIC_ABILITY = 14;
    public static final int EXCEPTION_ALARM = 32770;
    public static final int EXCEPTION_ALARMRECONNECT = 32774;
    public static final int EXCEPTION_AUDIOEXCHANGE = 32769;
    public static final int EXCEPTION_DISKFMT = 32785;
    public static final int EXCEPTION_EXCHANGE = 32768;
    public static final int EXCEPTION_PLAYBACK = 32784;
    public static final int EXCEPTION_PREVIEW = 32771;
    public static final int EXCEPTION_RECONNECT = 32773;
    public static final int EXCEPTION_SERIAL = 32772;
    public static final int EXCEPTION_SERIALRECONNECT = 32775;
    public static final int EZVIZ_CLASSSESSION_LEN = 64;
    public static final int EZVIZ_DEVICEID_LEN = 32;
    public static final int FAN_NUM = 8;
    public static final int FISHEYE_ABILITY = 1792;
    public static final int FPGA_NUM = 8;
    public static final int IPC_FRONT_PARAMETER = 5;
    public static final int IPC_FRONT_PARAMETER_V20 = 9;
    public static final int IPC_UPGRADE_DESCRIPTION = 6;
    public static final int IP_VIEW_DEV_ABILITY = 20;
    public static final int ITC_TRIGGER_MODE_ABILITY = 769;
    public static final int IT_DEVICE_ABILITY = 1281;
    public static final int IW_ESSID_MAX_SIZE = 32;
    public static final int JUDGE_MAX_VIDEOOUT_NUM = 9;
    public static final int LOCAL_AREA_NETWORK = 0;
    public static final int LOG_INFO_LEN = 11840;
    public static final int MACADDR_LEN = 6;
    public static final int MAIN_BOARD = 8;
    public static final int MATRIXDECODER_ABILITY = 512;
    public static final int MATRIXDECODER_ABILITY_V41 = 608;
    public static final int MAX_ABILITYTYPE_NUM = 12;
    public static final int MAX_ALARMHOSTKEYBOARD = 64;
    public static final int MAX_ALARMHOST_ALARMIN_NUM = 512;
    public static final int MAX_ALARMHOST_ALARMOUT_NUM = 512;
    public static final int MAX_ALARMHOST_SUBSYSTEM = 32;
    public static final int MAX_ALARMIN = 16;
    public static final int MAX_ALARMIN_V30 = 160;
    public static final int MAX_ALARMOUT = 4;
    public static final int MAX_ALARMOUT_V30 = 96;
    public static final int MAX_ALARMOUT_V40 = 4128;
    public static final int MAX_ANALOG_ALARMOUT = 32;
    public static final int MAX_ANALOG_CHANNUM = 32;
    public static final int MAX_AUDIO_V30 = 2;
    public static final int MAX_AUX_ALARM_NUM = 8;
    public static final int MAX_CHANNUM = 16;
    public static final int MAX_CHANNUM_V30 = 64;
    public static final int MAX_CRUISE_V30 = 256;
    public static final int MAX_DAYS = 7;
    public static final int MAX_DDNS_NUMS = 10;
    public static final int MAX_DISKNUM = 16;
    public static final int MAX_DISKNUM_V30 = 33;
    public static final int MAX_DOMAIN_NAME = 64;
    public static final int MAX_ETHERNET = 2;
    public static final int MAX_EXCEPTIONNUM_V30 = 32;
    public static final int MAX_IP_ALARMOUT = 64;
    public static final int MAX_IP_CHANNEL = 32;
    public static final int MAX_IP_DEVICE = 32;
    public static final int MAX_IP_DEVICE_V40 = 64;
    public static final int MAX_KEYBOARD_USER_NUM = 256;
    public static final int MAX_LINK = 6;
    public static final int MAX_NAMELEN = 16;
    public static final int MAX_NODE_NUM = 256;
    public static final int MAX_PRESET_NUM = 256;
    public static final int MAX_PRESET_V30 = 256;
    public static final int MAX_RIGHT = 32;
    public static final int MAX_SERVER_DEVICE_NUMBER = 16;
    public static final int MAX_SHELTERNUM = 4;
    public static final int MAX_STRINGNUM_V30 = 8;
    public static final int MAX_TIMESEGMENT = 4;
    public static final int MAX_TIMESEGMENT_V30 = 8;
    public static final int MAX_TRACK_V30 = 256;
    public static final int MAX_USERNUM_V30 = 32;
    public static final int MAX_VIDEOIN_TYPE_NUM = 10;
    public static final int MAX_WINDOW_V40 = 64;
    public static final int MAX_WIRELESS_ALARM_NUM = 8;
    public static final int MAX_ZONE_LINKAGE_CHAN_NUM = 4;
    public static final int MICROPHONE_NUM = 16;
    public static final int NAME_LEN = 32;
    public static final int NET_DVR_ARM_ALARMHOST_SUBSYSTEM = 2036;
    public static final int NET_DVR_AUDIOSTREAMDATA = 3;
    public static final int NET_DVR_CANCEL_SUBSYSTEM_BYPASS = 2029;
    public static final int NET_DVR_DEV_ADDRESS_MAX_LEN = 129;
    public static final int NET_DVR_FILE_EXCEPTION = 1004;
    public static final int NET_DVR_FILE_NOFIND = 1001;
    public static final int NET_DVR_FILE_SUCCESS = 1000;
    public static final int NET_DVR_GET_ALARMHOSTSUBSYSTEM_CFG = 2001;
    public static final int NET_DVR_GET_ALARMHOST_ENABLECFG = 1193;
    public static final int NET_DVR_GET_ALARMHOST_MAIN_STATUS = 1190;
    public static final int NET_DVR_GET_ALARMHOST_OTHER_STATUS = 1191;
    public static final int NET_DVR_GET_ALARMHOST_SUBSYSTEM_CFG_EX = 2030;
    public static final int NET_DVR_GET_ALARMINCFG_V30 = 1024;
    public static final int NET_DVR_GET_ALARMIN_PARAM = 1183;
    public static final int NET_DVR_GET_ALARMOUTCFG_V30 = 1026;
    public static final int NET_DVR_GET_AP_INFO_LIST = 305;
    public static final int NET_DVR_GET_AUDIO_ACTIVATION_CFG = 6326;
    public static final int NET_DVR_GET_CALLER_INFO = 16033;
    public static final int NET_DVR_GET_CALL_STATUS = 16034;
    public static final int NET_DVR_GET_COMPRESSCFG_AUD = 1058;
    public static final int NET_DVR_GET_COMPRESSCFG_V30 = 1040;
    public static final int NET_DVR_GET_CURRENT_VALID_PORT = 9300;
    public static final int NET_DVR_GET_DDNSCFG_V30 = 1010;
    public static final int NET_DVR_GET_DECODERCFG_V30 = 1042;
    public static final int NET_DVR_GET_DEVICECFG = 100;
    public static final int NET_DVR_GET_DEVICECFG_V40 = 1100;
    public static final int NET_DVR_GET_DIGITAL_CHANNEL_STATE = 6126;
    public static final int NET_DVR_GET_EXCEPTIONCFG_V40 = 6177;
    public static final int NET_DVR_GET_IPALARMOUTCFG = 1052;
    public static final int NET_DVR_GET_IPPARACFG_V31 = 1060;
    public static final int NET_DVR_GET_IPPARACFG_V40 = 1062;
    public static final int NET_DVR_GET_NETCFG_V30 = 1000;
    public static final int NET_DVR_GET_NTPCFG = 224;
    public static final int NET_DVR_GET_PICCFG_V30 = 1002;
    public static final int NET_DVR_GET_PRESET_NAME = 3383;
    public static final int NET_DVR_GET_RECORDCFG_V30 = 1004;
    public static final int NET_DVR_GET_SERVER_DEVICE_INFO = 16035;
    public static final int NET_DVR_GET_SHOWSTRING_V30 = 1030;
    public static final int NET_DVR_GET_TIMECFG = 118;
    public static final int NET_DVR_GET_TRIAL_HOST_STATUS = 6338;
    public static final int NET_DVR_GET_TRIAL_MICROPHONE_STATUS = 6336;
    public static final int NET_DVR_GET_TRIAL_SYSTEM_CFG = 6334;
    public static final int NET_DVR_GET_USERCFG_V30 = 1006;
    public static final int NET_DVR_GET_WIFI_CFG = 307;
    public static final int NET_DVR_GET_WIFI_STATUS = 310;
    public static final int NET_DVR_GET_ZEROCHANCFG = 1102;
    public static final int NET_DVR_GET_ZONE_CHANNEL_LINKAGE_CFG = 2208;
    public static final int NET_DVR_ISFINDING = 1002;
    public static final int NET_DVR_NOMOREFILE = 1003;
    public static final int NET_DVR_PLAYPAUSE = 3;
    public static final int NET_DVR_PLAYRESTART = 4;
    public static final int NET_DVR_PLAYSTART = 1;
    public static final int NET_DVR_REMOTECONTROL_GATEWAY = 16009;
    public static final int NET_DVR_SET_ALARMHOSTSUBSYSTEM_CFG = 2002;
    public static final int NET_DVR_SET_ALARMINCFG_V30 = 1025;
    public static final int NET_DVR_SET_ALARMIN_PARAM = 1182;
    public static final int NET_DVR_SET_ALARMOUTCFG_V30 = 1027;
    public static final int NET_DVR_SET_AUDIO_ACTIVATION_CFG = 6327;
    public static final int NET_DVR_SET_CALL_SIGNAL = 16036;
    public static final int NET_DVR_SET_COMPRESSCFG_V30 = 1041;
    public static final int NET_DVR_SET_DDNSCFG_V30 = 1011;
    public static final int NET_DVR_SET_DECODERCFG_V30 = 1043;
    public static final int NET_DVR_SET_DEVICECFG = 101;
    public static final int NET_DVR_SET_IPPARACFG_V31 = 1061;
    public static final int NET_DVR_SET_IPPARACFG_V40 = 1063;
    public static final int NET_DVR_SET_NETCFG_V30 = 1001;
    public static final int NET_DVR_SET_NTPCFG = 225;
    public static final int NET_DVR_SET_PICCFG_V30 = 1003;
    public static final int NET_DVR_SET_RECORDCFG_V30 = 1005;
    public static final int NET_DVR_SET_SHOWSTRING_V30 = 1031;
    public static final int NET_DVR_SET_SUBSYSTEM_BYPASS = 2028;
    public static final int NET_DVR_SET_TIMECFG = 119;
    public static final int NET_DVR_SET_USERCFG_V30 = 1007;
    public static final int NET_DVR_SET_WIFI_CFG = 306;
    public static final int NET_DVR_SET_ZEROCHANCFG = 1103;
    public static final int NET_DVR_STD_AUDIODATA = 5;
    public static final int NET_DVR_STD_VIDEODATA = 4;
    public static final int NET_DVR_STREAMDATA = 2;
    public static final int NET_DVR_SYSHEAD = 1;
    public static final int NET_IPC_GET_AUX_ALARMCFG = 3209;
    public static final int NET_IPC_SET_AUX_ALARMCFG = 3210;
    public static final int PASSWD_LEN = 16;
    public static final int PIC_CAPTURE_ABILITY = 1026;
    public static final int PREVIEW_RECONNECTSUCCESS = 32789;
    public static final int PTZ_PROTOCOL_NUM = 200;
    public static final int RESUME_EXCHANGE = 32791;
    public static final int SCREENCONTROL_ABILITY = 1536;
    public static final int SCREENSERVER_ABILITY = 1552;
    public static final int SDK_MAX_IP_LEN = 48;
    public static final int SERIALNO_LEN = 48;
    public static final int SERIAL_RECONNECTSUCCESS = 32776;
    public static final int SNAPCAMERA_ABILITY = 768;
    public static final int STEP_BACKUP = 3;
    public static final int STEP_READY = 0;
    public static final int STEP_RECV_DATA = 1;
    public static final int STEP_SEARCH = 255;
    public static final int STEP_UPGRADE = 2;
    public static final int VCA_CHAN_ABILITY = 272;
    public static final int VCA_DEV_ABILITY = 256;
    public static final int VIDEOPLATFORM_ABILITY = 528;
    public static final int WIDE_AREA_NETWORK = 1;
    public static final int WIFI_MAX_AP_COUNT = 20;
    public static final int WIFI_WEP_MAX_KEY_COUNT = 4;
    public static final int WIFI_WEP_MAX_KEY_LENGTH = 33;
    public static final int WIFI_WPA_PSK_MAX_KEY_LENGTH = 63;
    static HCNetSDK netSdk;

    static {
        netSdk = null;
        System.loadLibrary("opensslwrap");
        System.loadLibrary("HCCore");
        System.loadLibrary("HCCoreDevCfg");
        try {
            System.loadLibrary("HCPreview");
        } catch (UnsatisfiedLinkError e) {
        }
        try {
            System.loadLibrary("HCPlayBack");
        } catch (UnsatisfiedLinkError e2) {
        }
        try {
            System.loadLibrary("HCGeneralCfgMgr");
        } catch (UnsatisfiedLinkError e3) {
        }
        try {
            System.loadLibrary("HCVoiceTalk");
        } catch (UnsatisfiedLinkError e4) {
        }
        try {
            System.loadLibrary("HCIndustry");
        } catch (UnsatisfiedLinkError e5) {
        }
        try {
            System.loadLibrary("HCDisplay");
        } catch (UnsatisfiedLinkError e6) {
        }
        try {
            System.loadLibrary("HCAlarm");
        } catch (UnsatisfiedLinkError e7) {
        }
        try {
            System.loadLibrary("SystemTransform");
        } catch (UnsatisfiedLinkError e8) {
        }
        System.loadLibrary("hcnetsdk");
    }

    public static synchronized HCNetSDK getInstance() {
        HCNetSDK hCNetSDK;
        synchronized (HCNetSDK.class) {
            if (netSdk == null) {
                netSdk = new HCNetSDK();
            }
            hCNetSDK = netSdk;
        }
        return hCNetSDK;
    }

    public native boolean NET_DVR_ActivateDevice(String str, int i, NET_DVR_ACTIVATECFG net_dvr_activatecfg);

    public native boolean NET_DVR_AlarmHostClearAlarm(int i, int i2);

    public native boolean NET_DVR_AlarmHostCloseAlarmChan(int i, NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup);

    public native boolean NET_DVR_AlarmHostSetupAlarmChan(int i, NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup);

    public native boolean NET_DVR_AlarmHostSubSystemCloseAlarmChan(int i, int i2);

    public native boolean NET_DVR_AlarmHostSubSystemSetupAlarmChan(int i, int i2);

    public native boolean NET_DVR_BypassAlarmChan(int i, NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup);

    public native boolean NET_DVR_CaptureJPEGPicture(int i, int i2, NET_DVR_JPEGPARA net_dvr_jpegpara, String str);

    public native boolean NET_DVR_CaptureJPEGPicture_NEW(int i, int i2, NET_DVR_JPEGPARA net_dvr_jpegpara, byte[] bArr, int i3, INT_PTR int_ptr);

    public native boolean NET_DVR_Cleanup();

    public native boolean NET_DVR_ClickKey(int i, int i2);

    public native boolean NET_DVR_ClientGetVideoEffect(int i, NET_DVR_VIDEOEFFECT net_dvr_videoeffect);

    public native boolean NET_DVR_ClientSetVideoEffect(int i, NET_DVR_VIDEOEFFECT net_dvr_videoeffect);

    public native boolean NET_DVR_CloseAlarmChan_V30(int i);

    public native boolean NET_DVR_CloseFormatHandle(int i);

    public native boolean NET_DVR_CloseUpgradeHandle(int i);

    public native int NET_DVR_CreateEzvizUser(NET_DVR_EZVIZ_USER_LOGIN_INFO net_dvr_ezviz_user_login_info, NET_DVR_DEVICEINFO_V30 net_dvr_deviceinfo_v30);

    public native boolean NET_DVR_DeleteEzvizUser(int i);

    public native boolean NET_DVR_FindClose_V30(int i);

    public native long NET_DVR_FindDVRLog_V30(long j, long j2, int i, int i2, NET_DVR_TIME net_dvr_time, NET_DVR_TIME net_dvr_time2, boolean z);

    public native int NET_DVR_FindFileByEvent(int i, NET_DVR_SEARCH_EVENT_PARAM net_dvr_search_event_param);

    public native int NET_DVR_FindFile_V30(int i, NET_DVR_FILECOND net_dvr_filecond);

    public native boolean NET_DVR_FindLogClose_V30(long j);

    public native int NET_DVR_FindNextEvent(int i, NET_DVR_SEARCH_EVENT_RET net_dvr_search_event_ret);

    public native int NET_DVR_FindNextFile_V30(int i, NET_DVR_FINDDATA_V30 net_dvr_finddata_v30);

    public native long NET_DVR_FindNextLog_V30(long j, NET_DVR_LOG_V30 net_dvr_log_v30);

    public native int NET_DVR_FormatDisk(int i, int i2);

    public native boolean NET_DVR_GetAddrInfoByServer(int i, NET_DVR_ADDR_QUERY_COND net_dvr_addr_query_cond, NET_DVR_ADDR_QUERY_RET net_dvr_addr_query_ret);

    public native boolean NET_DVR_GetAlarmOut_V30(int i, NET_DVR_ALARMOUTSTATUS_V30 net_dvr_alarmoutstatus_v30);

    public native boolean NET_DVR_GetCompressionAbility(int i, int i2, NET_DVR_COMPRESSIONCFG_ABILITY net_dvr_compressioncfg_ability);

    public native boolean NET_DVR_GetCurrentAudioCompress(int i, NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio);

    public native boolean NET_DVR_GetCurrentAudioCompress_V50(int i, NET_DVR_AUDIO_CHANNEL net_dvr_audio_channel, NET_DVR_COMPRESSION_AUDIO net_dvr_compression_audio);

    public native boolean NET_DVR_GetDVRConfig(int i, int i2, int i3, NET_DVR_CONFIG net_dvr_config);

    public boolean NET_DVR_GetDVRIPByResolveSvr_EX(String str, short s, String str2, short s2, String str3, short s3, NET_DVR_RESOLVE_DEVICEINFO net_dvr_resolve_deviceinfo) {
        byte[] bArr = new byte[Constants.SUPPORT_SSE];
        try {
            byte[] bytes = str2.getBytes("GB2312");
            for (int i = STEP_READY; i < bytes.length; i += WIDE_AREA_NETWORK) {
                bArr[i] = bytes[i];
            }
            return NET_DVR_GetDVRIPByResolveSvr_EX(str, s, bArr, (short) bytes.length, str3, s3, net_dvr_resolve_deviceinfo);
        } catch (Exception e) {
            return false;
        }
    }

    public native boolean NET_DVR_GetDVRIPByResolveSvr_EX(String str, short s, byte[] bArr, short s2, String str2, short s3, NET_DVR_RESOLVE_DEVICEINFO net_dvr_resolve_deviceinfo);

    public native boolean NET_DVR_GetDVRWorkState_V30(int i, NET_DVR_WORKSTATE_V30 net_dvr_workstate_v30);

    public native boolean NET_DVR_GetDeviceConfig(int i, int i2, NET_DVR_CONDITION net_dvr_condition, NET_DVR_CONFIG net_dvr_config);

    public native boolean NET_DVR_GetDeviceStatus(int i, int i2, NET_DVR_CONDITION net_dvr_condition, NET_DVR_STATUS net_dvr_status);

    public native int NET_DVR_GetDownloadPos(int i);

    public native String NET_DVR_GetErrorMsg(INT_PTR int_ptr);

    public native int NET_DVR_GetFileByName(int i, String str, String str2);

    public native int NET_DVR_GetFileByTime(int i, int i2, NET_DVR_TIME net_dvr_time, NET_DVR_TIME net_dvr_time2, String str);

    public native boolean NET_DVR_GetFormatProgress(int i, INT_PTR int_ptr, INT_PTR int_ptr2, INT_PTR int_ptr3);

    public native int NET_DVR_GetLastError();

    public native boolean NET_DVR_GetPTZProtocol(int i, NET_DVR_PTZCFG net_dvr_ptzcfg);

    public native int NET_DVR_GetPlayBackPos(int i);

    public native int NET_DVR_GetSDKBuildVersion();

    public native boolean NET_DVR_GetSDKLocalConfig(NET_DVR_SDKLOCAL_CFG net_dvr_sdklocal_cfg);

    public native int NET_DVR_GetSDKVersion();

    public native int NET_DVR_GetUpgradeProgress(int i);

    public native int NET_DVR_GetUpgradeState(int i);

    public native int NET_DVR_GetUpgradeStep(int i, INT_PTR int_ptr);

    public native boolean NET_DVR_GetUpnpNatState(int i, NET_DVR_UPNP_NAT_STATE net_dvr_upnp_nat_state);

    public native boolean NET_DVR_GetXMLAbility(int i, int i2, byte[] bArr, int i3, byte[] bArr2, int i4, INT_PTR int_ptr);

    public native boolean NET_DVR_Init();

    public native boolean NET_DVR_InquestGetPIPStatus_V40(int i, NET_DVR_INQUEST_ROOM net_dvr_inquest_room, NET_DVR_INQUEST_PIP_STATUS_V40 net_dvr_inquest_pip_status_v40);

    public native boolean NET_DVR_InquestSetPIPStatus_V40(int i, NET_DVR_INQUEST_ROOM net_dvr_inquest_room, NET_DVR_INQUEST_PIP_STATUS_V40 net_dvr_inquest_pip_status_v40);

    public native boolean NET_DVR_InquestStartCDW_V30(int i, NET_DVR_INQUEST_ROOM net_dvr_inquest_room, boolean z);

    public native boolean NET_DVR_InquestStopCDW_V30(int i, NET_DVR_INQUEST_ROOM net_dvr_inquest_room, boolean z);

    public int NET_DVR_Login_V30(String str, int i, String str2, String str3, NET_DVR_DEVICEINFO_V30 net_dvr_deviceinfo_v30) {
        if (str2.length() > MAX_WINDOW_V40) {
            return -2;
        }
        byte[] bArr = new byte[MAX_WINDOW_V40];
        try {
            byte[] bytes = str2.getBytes("GB2312");
            for (int i2 = STEP_READY; i2 < bytes.length; i2 += WIDE_AREA_NETWORK) {
                bArr[i2] = bytes[i2];
            }
            return NET_DVR_Login_V30(str, i, bArr, str3, net_dvr_deviceinfo_v30);
        } catch (Exception e) {
            return -2;
        }
    }

    public native int NET_DVR_Login_V30(String str, int i, byte[] bArr, String str2, NET_DVR_DEVICEINFO_V30 net_dvr_deviceinfo_v30);

    public native boolean NET_DVR_Logout_V30(int i);

    public native boolean NET_DVR_MakeKeyFrame(int i, int i2);

    public native boolean NET_DVR_MakeKeyFrameSub(int i, int i2);

    public native boolean NET_DVR_PTZControl(int i, int i2, int i3);

    public native boolean NET_DVR_PTZControlWithSpeed(int i, int i2, int i3, int i4);

    public native boolean NET_DVR_PTZControlWithSpeed_Other(int i, int i2, int i3, int i4, int i5);

    public native boolean NET_DVR_PTZControl_Other(int i, int i2, int i3, int i4);

    public native boolean NET_DVR_PTZCruise(int i, int i2, byte b, byte b2, short s);

    public native boolean NET_DVR_PTZCruise_Other(int i, int i2, int i3, byte b, byte b2, short s);

    public native boolean NET_DVR_PTZPreset(int i, int i2, int i3);

    public native boolean NET_DVR_PTZPreset_Other(int i, int i2, int i3, int i4);

    public native boolean NET_DVR_PTZSelZoomIn(int i, NET_DVR_POINT_FRAME net_dvr_point_frame);

    public native boolean NET_DVR_PTZSelZoomIn_EX(int i, int i2, NET_DVR_POINT_FRAME net_dvr_point_frame);

    public native boolean NET_DVR_PTZTrack(int i, int i2);

    public native boolean NET_DVR_PTZTrack_Other(int i, int i2, int i3);

    public native int NET_DVR_PlayBackByName(int i, String str);

    public native int NET_DVR_PlayBackByTime(int i, int i2, NET_DVR_TIME net_dvr_time, NET_DVR_TIME net_dvr_time2);

    public native boolean NET_DVR_PlayBackControl_V40(int i, int i2, byte[] bArr, int i3, NET_DVR_PLAYBACK_INFO net_dvr_playback_info);

    public native boolean NET_DVR_PlayBackControl_V50(int i, int i2, NET_DVR_PLAYBACK_CONTROL_COND net_dvr_playback_control_cond, NET_DVR_PLAYBACK_CONTROL_RET net_dvr_playback_control_ret);

    public native boolean NET_DVR_PlayBackSaveData(int i, String str);

    public native int NET_DVR_RealPlay_V30(int i, NET_DVR_CLIENTINFO net_dvr_clientinfo, RealPlayCallBack realPlayCallBack, boolean z);

    public native int NET_DVR_RealPlay_V40(int i, NET_DVR_PREVIEWINFO net_dvr_previewinfo, RealPlayCallBack realPlayCallBack);

    public native boolean NET_DVR_RebootDVR(int i);

    public native boolean NET_DVR_RemoteControl(int i, int i2, NET_DVR_CONTROL net_dvr_control);

    public native boolean NET_DVR_SaveRealData(int i, String str);

    public native boolean NET_DVR_SendTo232Port(int i, byte[] bArr, int i2);

    public native boolean NET_DVR_SendToSerialPort(int i, int i2, int i3, byte[] bArr, int i4);

    public native boolean NET_DVR_SerialSend(int i, int i2, byte[] bArr, int i3);

    public native int NET_DVR_SerialStart(int i, int i2, SerialDataCallBack serialDataCallBack);

    public native int NET_DVR_SerialStart_V40(int i, NET_DVR_SERIAL_COND net_dvr_serial_cond, SerialDataCallBackV40 serialDataCallBackV40);

    public native boolean NET_DVR_SerialStop(int i);

    public native boolean NET_DVR_SetAlarmOut(int i, int i2, int i3);

    public native boolean NET_DVR_SetConnectTime(int i);

    public native boolean NET_DVR_SetDVRConfig(int i, int i2, int i3, NET_DVR_CONFIG net_dvr_config);

    public native boolean NET_DVR_SetDVRMessageCallBack_V30(AlarmCallBack_V30 alarmCallBack_V30);

    public native boolean NET_DVR_SetDeviceConfig(int i, int i2, NET_DVR_CONDITION net_dvr_condition, NET_DVR_CONFIG net_dvr_config);

    public native boolean NET_DVR_SetExceptionCallBack(ExceptionCallBack exceptionCallBack);

    public native boolean NET_DVR_SetLogToFile(int i, String str, boolean z);

    public native boolean NET_DVR_SetNetworkEnvironment(int i);

    public native boolean NET_DVR_SetPlayDataCallBack(int i, PlaybackCallBack playbackCallBack);

    public native boolean NET_DVR_SetReconnect(int i, boolean z);

    public native boolean NET_DVR_SetRecvTimeOut(int i);

    public native boolean NET_DVR_SetSDKLocalConfig(NET_DVR_SDKLOCAL_CFG net_dvr_sdklocal_cfg);

    public native boolean NET_DVR_SetSimAbilityPath(String str, String str2);

    public native int NET_DVR_SetupAlarmChan_V30(int i);

    public native boolean NET_DVR_ShutDownDVR(int i);

    public native boolean NET_DVR_StartDVRRecord(int i, int i2, int i3);

    public native int NET_DVR_StartVoiceCom_MR_V30(int i, int i2, VoiceDataCallBack voiceDataCallBack);

    public native boolean NET_DVR_StopDVRRecord(int i, int i2);

    public native boolean NET_DVR_StopGetFile(int i);

    public native boolean NET_DVR_StopPlayBack(int i);

    public native boolean NET_DVR_StopPlayBackSave(int i);

    public native boolean NET_DVR_StopRealPlay(int i);

    public native boolean NET_DVR_StopSaveRealData(int i);

    public native boolean NET_DVR_StopVoiceCom(int i);

    public native boolean NET_DVR_UnBypassAlarmChan(int i, NET_DVR_ALARMIN_SETUP net_dvr_alarmin_setup);

    public native boolean NET_DVR_UpdateRecordIndex(int i, int i2);

    public native int NET_DVR_Upgrade(int i, String str);

    public native boolean NET_DVR_VoiceComSendData(int i, byte[] bArr, int i2);

    public native int NET_DVR_ZeroStartPlay(int i, NET_DVR_CLIENTINFO net_dvr_clientinfo, RealPlayCallBack realPlayCallBack, boolean z);

    public native boolean NET_DVR_ZeroStopPlay(int i);
}
