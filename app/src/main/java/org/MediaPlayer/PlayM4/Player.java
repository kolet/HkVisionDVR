package org.MediaPlayer.PlayM4;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import org.MediaPlayer.PlayM4.PlayerCallBack.PlayerDecodeCB;
import org.MediaPlayer.PlayM4.PlayerCallBack.PlayerDisplayCB;
import org.MediaPlayer.PlayM4.PlayerCallBack.PlayerFileRefCB;
import org.MediaPlayer.PlayM4.PlayerCallBack.PlayerPlayEndCB;
import org.MediaPlayer.PlayM4.PlayerCallBack.PlayerPreRecordCB;

public class Player {
    private static final int CPU_ARMv7 = 2;
    private static final int CPU_NEON = 3;
    private static final int CPU_NOT_ARM = 0;
    private static final int CPU_NOT_ARMv7 = 1;
    public static final int MAX_PORT = 16;
    public static final int MAX_REGION_NUM = 4;
    public static final int PLAYM4_FAIL = 0;
    public static final int PLAYM4_OK = 1;
    public static final int STREAM_FILE = 1;
    public static final int STREAM_REALTIME = 0;
    private static final String TAG = "PlayerSDK";
    public static final int VOLUME_DEFAULT = 32767;
    public static final int VOLUME_MAX = 65535;
    public static final int VOLUME_MUTE = 0;
    private static Player mPlayer;

    public class MPInteger {
        public int value;
    }

    public class MPRect {
        public int bottom;
        public int left;
        public int right;
        public int top;
    }

    public class MPSystemTime {
        public int day;
        public int hour;
        public int min;
        public int month;
        public int ms;
        public int sec;
        public int year;
    }

    public class MPVR_DISPLAY_EFFECT {
        public static final int VR_ET_FISH_LATITUDE_WALL = 8;
        public static final int VR_ET_FISH_PANORAMA_CEILING180 = 5;
        public static final int VR_ET_FISH_PANORAMA_CEILING360 = 4;
        public static final int VR_ET_FISH_PANORAMA_FLOOR180 = 7;
        public static final int VR_ET_FISH_PANORAMA_FLOOR360 = 6;
        public static final int VR_ET_FISH_PTZ_CEILING = 1;
        public static final int VR_ET_FISH_PTZ_FLOOR = 2;
        public static final int VR_ET_FISH_PTZ_WALL = 3;
        public static final int VR_ET_NULL = 0;
        public static final int VR_ET_REDBLUE_3D = 9;
    }

    public class MPVR_FISH_PARAM {
        public float PTZX;
        public float PTZY;
        public float angle;
        public float xLeft;
        public float xRight;
        public float yBottom;
        public float yTop;
        public float zoom;
    }

//    public class MP_DECODE_TYPE {
//        public static int DECODE_ALL;
//        public static int DECODE_NONE;
//        public static int DECODE_VIDEO_KEYFRAME;
//
//        static {
//            DECODE_ALL = Player.STREAM_REALTIME;
//            DECODE_VIDEO_KEYFRAME = Player.STREAM_FILE;
//            DECODE_NONE = Player.CPU_ARMv7;
//        }
//    }

    static {
        mPlayer = null;
        System.loadLibrary("CpuFeatures");
    }

    private Player() {
        int GetCpuFeatures = GetCpuFeatures();
        if (CPU_NEON == GetCpuFeatures) {
            System.loadLibrary("PlayCtrl");
        } else if (CPU_ARMv7 == GetCpuFeatures) {
            System.loadLibrary("PlayCtrl_v7");
        } else if (STREAM_FILE == GetCpuFeatures) {
            System.loadLibrary("PlayCtrl_v5");
        } else {
            Log.i(TAG, "Not a arm CPU! FAIL to load PlayCtrl!");
        }
        SetAndroidSDKVersion(VERSION.SDK_INT);
    }

    private native int CloseFile(int i);

    private native int CloseStream(int i);

    private native int Fast(int i);

    private native int FreePort(int i);

    private native int GetBMP(int i, byte[] bArr, int i2, MPInteger mPInteger);

    private native int GetBufferValue(int i, int i2);

    private native int GetCpuFeatures();

    private native int GetCurrentFrameNum(int i);

    private native int GetCurrentFrameRate(int i);

    private native int GetDisParam(int i, int i2, MPVR_FISH_PARAM mpvr_fish_param);

    private native int GetDisplayBuf(int i);

    private native long GetFileTime(int i);

    private native int GetFileTotalFrames(int i);

    private native int GetJPEG(int i, byte[] bArr, int i2, MPInteger mPInteger);

    private native int GetLastError(int i);

    private native int GetPictureSize(int i, MPInteger mPInteger, MPInteger mPInteger2);

    private native float GetPlayPos(int i);

    private native int GetPlayedFrames(int i);

    private native int GetPlayedTime(int i);

    private native int GetPlayedTimeEx(int i);

    private native int GetPort();

    private native int GetSdkVersion();

    private native int GetSourceBufferRemain(int i);

    private native int GetSystemTime(int i, MPSystemTime mPSystemTime);

    private native int InputData(int i, byte[] bArr, int i2);

    private native int OpenFile(int i, byte[] bArr);

    private native int OpenStream(int i, byte[] bArr, int i2, int i3);

    private native int Pause(int i, int i2);

    private native int Play(int i, Surface surface);

    private native int PlaySound(int i);

    private native int ResetBuffer(int i, int i2);

    private native int ResetSourceBufFlag(int i);

    private native int ResetSourceBuffer(int i);

    private native void SetAndroidSDKVersion(int i);

    private native int SetCurrentFrameNum(int i, int i2);

    private native int SetDecodeCallback(int i, PlayerDecodeCB playerDecodeCB);

    private native int SetDecodeFrameType(int i, int i2);

    private native int SetDisEffect(int i, int i2, int i3);

    private native int SetDisParam(int i, int i2, MPVR_FISH_PARAM mpvr_fish_param);

    private native int SetDisplayBuf(int i, int i2);

    private native int SetDisplayCallback(int i, PlayerDisplayCB playerDisplayCB);

    private native int SetDisplayRegion(int i, int i2, MPRect mPRect, Surface surface, int i3);

    private native int SetFileEndCallback(int i, PlayerPlayEndCB playerPlayEndCB);

    private native int SetFileRefCallBack(int i, PlayerFileRefCB playerFileRefCB);

    private native int SetHDPriority(int i, int i2);

    private native int SetImageCorrection(int i, int i2);

    private native int SetPlayPos(int i, float f);

    private native int SetPlayedTimeEx(int i, int i2);

    private native int SetPreRecordCallBack(int i, PlayerPreRecordCB playerPreRecordCB);

    private native int SetPreRecordFlag(int i, int i2);

    private native int SetSecretKey(int i, int i2, byte[] bArr, int i3);

    private native int SetStreamOpenMode(int i, int i2);

    private native int SetVideoWindow(int i, int i2, Surface surface);

    private native int Slow(int i);

    private native int Stop(int i);

    private native int StopSound();

    private native int VerticalFlip(int i, int i2);

    public static Player getInstance() {
        if (VERSION.SDK_INT < 9) {
            Log.e(TAG, "Android Level Lower than 2.3!");
            return null;
        }
        if (mPlayer == null) {
            try {
                mPlayer = new Player();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mPlayer;
    }

    public boolean closeFile(int i) {
        return CloseFile(i) != 0;
    }

    public boolean closeStream(int i) {
        return CloseStream(i) != 0;
    }

    public boolean fast(int i) {
        return Fast(i) != 0;
    }

    public boolean freePort(int i) {
        return FreePort(i) != 0;
    }

    public boolean getBMP(int i, byte[] bArr, int i2, MPInteger mPInteger) {
        return GetBMP(i, bArr, i2, mPInteger) != 0;
    }

    public int getBufferValue(int i, int i2) {
        return GetBufferValue(i, i2);
    }

    public int getCurrentFrameNum(int i) {
        return GetCurrentFrameNum(i);
    }

    public int getCurrentFrameRate(int i) {
        return GetCurrentFrameRate(i);
    }

    public int getDisplayBuf(int i) {
        return GetDisplayBuf(i);
    }

    public boolean getFECDisplayParam(int i, int i2, MPVR_FISH_PARAM mpvr_fish_param) {
        return GetDisParam(i, i2, mpvr_fish_param) != 0;
    }

    public long getFileTime(int i) {
        return GetFileTime(i);
    }

    public int getFileTotalFrames(int i) {
        return GetFileTotalFrames(i);
    }

    public boolean getJPEG(int i, byte[] bArr, int i2, MPInteger mPInteger) {
        return GetJPEG(i, bArr, i2, mPInteger) != 0;
    }

    public int getLastError(int i) {
        return GetLastError(i);
    }

    public boolean getPictureSize(int i, MPInteger mPInteger, MPInteger mPInteger2) {
        return GetPictureSize(i, mPInteger, mPInteger2) != 0;
    }

    public float getPlayPos(int i) {
        return GetPlayPos(i);
    }

    public int getPlayedFrames(int i) {
        return GetPlayedFrames(i);
    }

    public int getPlayedTime(int i) {
        return GetPlayedTime(i);
    }

    public int getPlayedTimeEx(int i) {
        return GetPlayedTimeEx(i);
    }

    public int getPort() {
        return GetPort();
    }

    public int getSdkVersion() {
        return GetSdkVersion();
    }

    public int getSourceBufferRemain(int i) {
        return GetSourceBufferRemain(i);
    }

    public boolean getSystemTime(int i, MPSystemTime mPSystemTime) {
        return GetSystemTime(i, mPSystemTime) != 0;
    }

    public boolean inputData(int i, byte[] bArr, int i2) {
        return InputData(i, bArr, i2) != 0;
    }

    public boolean openFile(int i, String str) {
        byte[] bArr = null;
        if (str != null) {
            byte[] bytes = str.getBytes();
            if (bytes == null) {
                return false;
            }
            try {
                bArr = new byte[(bytes.length + STREAM_FILE)];
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i2 = STREAM_REALTIME; i2 < bytes.length; i2 += STREAM_FILE) {
                bArr[i2] = bytes[i2];
            }
            bArr[bArr.length - 1] = (byte) 0;
        }
        return OpenFile(i, bArr) != 0;
    }

    public boolean openStream(int i, byte[] bArr, int i2, int i3) {
        return OpenStream(i, bArr, i2, i3) != 0;
    }

    public boolean pause(int i, int i2) {
        return Pause(i, i2) != 0;
    }

    public boolean play(int i, SurfaceHolder surfaceHolder) {
        Surface surface = null;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
            if (surface == null) {
                return false;
            }
            if (!surface.isValid()) {
                Log.e(TAG, "Surface Invalid!");
                return false;
            }
        }
        if (Play(i, surface) != 0) {
            return true;
        }
        Log.e(TAG, "Play false!");
        return false;
    }

    public boolean playSound(int i) {
        return PlaySound(i) != 0;
    }

    public boolean resetBuffer(int i, int i2) {
        return ResetBuffer(i, i2) != 0;
    }

    public boolean resetSourceBuffer(int i) {
        return ResetSourceBuffer(i) != 0;
    }

    public boolean setCurrentFrameNum(int i, int i2) {
        return SetCurrentFrameNum(i, i2) != 0;
    }

    public boolean setDecodeCB(int i, PlayerDecodeCB playerDecodeCB) {
        return SetDecodeCallback(i, playerDecodeCB) != 0;
    }

    public boolean setDecodeFrameType(int i, int i2) {
        return SetDecodeFrameType(i, i2) != 0;
    }

    public boolean setDisplayBuf(int i, int i2) {
        return SetDisplayBuf(i, i2) != 0;
    }

    public boolean setDisplayCB(int i, PlayerDisplayCB playerDisplayCB) {
        return SetDisplayCallback(i, playerDisplayCB) != 0;
    }

    public boolean setDisplayRegion(int i, int i2, MPRect mPRect, SurfaceHolder surfaceHolder, int i3) {
        Surface surface = null;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
            if (surface == null) {
                return false;
            }
            if (!surface.isValid()) {
                Log.e(TAG, "Surface Invalid!");
                return false;
            }
        }
        return SetDisplayRegion(i, i2, mPRect, surface, i3) != 0;
    }

    public boolean setFECDisplayEffect(int i, int i2, int i3) {
        return SetDisEffect(i, i2, i3) != 0;
    }

    public boolean setFECDisplayParam(int i, int i2, MPVR_FISH_PARAM mpvr_fish_param) {
        return SetDisParam(i, i2, mpvr_fish_param) != 0;
    }

    public boolean setFileEndCB(int i, PlayerPlayEndCB playerPlayEndCB) {
        return SetFileEndCallback(i, playerPlayEndCB) != 0;
    }

    public boolean setFileRefCB(int i, PlayerFileRefCB playerFileRefCB) {
        return SetFileRefCallBack(i, playerFileRefCB) != 0;
    }

    public boolean setHardDecode(int i, int i2) {
        return SetHDPriority(i, i2) != 0;
    }

    public boolean setImageCorrection(int i, int i2) {
        return SetImageCorrection(i, i2) != 0;
    }

    public boolean setPlayPos(int i, float f) {
        return SetPlayPos(i, f) != 0;
    }

    public boolean setPlayedTimeEx(int i, int i2) {
        if (i2 >= 0) {
            return SetPlayedTimeEx(i, i2) != 0;
        } else {
            Log.e(TAG, "nTime less than 0!");
            return false;
        }
    }

    public boolean setPreRecordCallBack(int i, PlayerPreRecordCB playerPreRecordCB) {
        return SetPreRecordCallBack(i, playerPreRecordCB) != 0;
    }

    public boolean setPreRecordFlag(int i, boolean z) {
        if (z) {
            if (SetPreRecordFlag(i, STREAM_FILE) == 0) {
                return false;
            }
        } else if (SetPreRecordFlag(i, STREAM_REALTIME) == 0) {
            return false;
        }
        return true;
    }

    public boolean setSecretKey(int i, int i2, byte[] bArr, int i3) {
        return SetSecretKey(i, i2, bArr, i3) != 0;
    }

    public boolean setStreamOpenMode(int i, int i2) {
        return SetStreamOpenMode(i, i2) != 0;
    }

    public boolean setVideoWindow(int i, int i2, SurfaceHolder surfaceHolder) {
        Surface surface = null;
        if (surfaceHolder != null) {
            surface = surfaceHolder.getSurface();
            if (surface == null) {
                return false;
            }
            if (!surface.isValid()) {
                Log.e(TAG, "Surface Invalid!");
                return false;
            }
        }
        return SetVideoWindow(i, i2, surface) != 0;
    }

    public boolean slow(int i) {
        return Slow(i) != 0;
    }

    public boolean stop(int i) {
        return Stop(i) != 0;
    }

    public boolean stopSound() {
        return StopSound() != 0;
    }

    public boolean verticalFlip(int i, int i2) {
        return verticalFlip(i, i2);
    }
}
