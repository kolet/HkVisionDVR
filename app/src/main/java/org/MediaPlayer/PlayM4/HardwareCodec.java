package org.MediaPlayer.PlayM4;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.media.MediaFormat;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Surface;
import java.nio.ByteBuffer;

public class HardwareCodec {
    private static final String TAG = "GPU";
    private boolean isInit;
    private boolean isStart;
    private MediaCodec mCodec;
    private MediaFormat mFormat;
    private ByteBuffer mInputBuffer;
    private ByteBuffer[] mInputBuffers;
    private int mOutputIndex;
    private BufferInfo mOutputInfo;

    protected HardwareCodec() {
        this.mOutputIndex = -1;
        this.isInit = false;
        this.isStart = false;
        this.mCodec = null;
        this.mFormat = null;
        this.mOutputInfo = null;
        this.mInputBuffer = null;
        this.mInputBuffers = null;
        this.mCodec = null;
        this.mFormat = null;
        this.isInit = false;
        this.isStart = false;
        this.mOutputInfo = null;
        this.mInputBuffer = null;
        this.mOutputIndex = -1;
    }

    private MediaCodecInfo chooseCodec(String str) {
        int codecCount = MediaCodecList.getCodecCount();
        for (int i = 0; i < codecCount; i++) {
            MediaCodecInfo codecInfoAt = MediaCodecList.getCodecInfoAt(i);
            if (!codecInfoAt.isEncoder()) {
                String[] supportedTypes = codecInfoAt.getSupportedTypes();
                for (String equalsIgnoreCase : supportedTypes) {
                    if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                        return codecInfoAt;
                    }
                }
                continue;
            }
        }
        return null;
    }

    protected int CodecFlush() {
        if (this.mCodec == null) {
            return -38;
        }
        try {
            this.mCodec.flush();
            return 0;
        } catch (IllegalStateException e) {
            return -99;
        }
    }

    protected int CodecInput(byte[] bArr, int i, int i2, int i3) {
        if (!this.isStart) {
            return 2;
        }
        if (this.mCodec == null) {
            return -38;
        }
        try {
            int dequeueInputBuffer = this.mCodec.dequeueInputBuffer((long) i3);
            if (dequeueInputBuffer < 0) {
                return -35;
            }
            this.mInputBuffer = this.mInputBuffers[dequeueInputBuffer];
            if (this.mInputBuffer == null) {
                return -36;
            }
            if (this.mInputBuffer.capacity() < i) {
                return -40;
            }
            this.mInputBuffer.clear();
            this.mInputBuffer.put(bArr, 0, i);
            this.mCodec.queueInputBuffer(dequeueInputBuffer, 0, i, (long) i2, 0);
            return 0;
        } catch (IllegalStateException e) {
            return -99;
        } catch (Exception e2) {
            return -99;
        }
    }

    protected long CodecOutput() {
        if (!this.isStart) {
            return 2;
        }
        if (this.mCodec == null || this.mOutputInfo == null) {
            return -38;
        }
        try {
            this.mOutputIndex = this.mCodec.dequeueOutputBuffer(this.mOutputInfo, 0);
            if (this.mOutputIndex == -1) {
                return -1;
            }
            if (this.mOutputIndex == -3) {
                return -3;
            }
            if (this.mOutputIndex != -2) {
                return this.mOutputIndex < 0 ? -37 : this.mOutputInfo.size > 0 ? this.mOutputInfo.presentationTimeUs : 0;
            } else {
                Log.e(TAG, "Output format changed: " + this.mCodec.getOutputFormat());
                return -2;
            }
        } catch (IllegalStateException e) {
            return -99;
        } catch (Exception e2) {
            return -99;
        }
    }

    protected int CodecRender() {
        if (this.mCodec == null) {
            return -38;
        }
        try {
            if (this.mOutputIndex > 0) {
                this.mCodec.releaseOutputBuffer(this.mOutputIndex, true);
            }
            return 0;
        } catch (IllegalStateException e) {
            return -99;
        } catch (Exception e2) {
            return -99;
        }
    }

    protected int CodecStop() {
        if (this.mCodec == null) {
            return -38;
        }
        try {
            this.mCodec.flush();
            this.mCodec.stop();
            this.mCodec.release();
            this.isInit = false;
            this.isStart = false;
            return 0;
        } catch (IllegalStateException e) {
            return -99;
        } catch (Exception e2) {
            return -99;
        }
    }

    protected int Init(int i, int i2) {
        if (VERSION.SDK_INT < 16) {
            return -33;
        }
        if (this.isInit) {
            return 2;
        }
        if (i <= 0 || i2 <= 0) {
            return 1;
        }
        try {
            this.mFormat = MediaFormat.createVideoFormat("video/avc", i, i2);
            MediaCodecInfo chooseCodec = chooseCodec("video/avc");
            if (chooseCodec == null) {
                return -34;
            }
            this.mCodec = MediaCodec.createByCodecName(chooseCodec.getName());
            this.isInit = true;
            return 0;
        } catch (Exception e) {
            return -99;
        }
    }

    protected int Start(Surface surface) {
        if (this.isStart || !this.isInit) {
            return 2;
        }
        if (surface == null || this.mCodec == null || this.mFormat == null) {
            return -38;
        }
        try {
            this.mCodec.configure(this.mFormat, surface, null, 0);
            this.mCodec.start();
            this.mInputBuffers = this.mCodec.getInputBuffers();
            this.mOutputInfo = new BufferInfo();
            this.isStart = true;
            return 0;
        } catch (IllegalArgumentException e) {
            return -99;
        } catch (IllegalStateException e2) {
            return -99;
        } catch (Exception e3) {
            return -99;
        }
    }
}
