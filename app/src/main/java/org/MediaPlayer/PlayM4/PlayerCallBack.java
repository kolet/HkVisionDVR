package org.MediaPlayer.PlayM4;

public class PlayerCallBack {

    public interface PlayerPlayEndCB {
        void onPlayEnd(int i);
    }

    public interface PlayerDisplayCB {
        void onDisplay(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7);
    }

    public interface PlayerFileRefCB {
        void onFileRefDone(int i);
    }

    public interface PlayerPreRecordCB {
        void onPreRecord(int i, byte[] bArr, int i2);
    }

    public interface PlayerDecodeCB {
        void onDecode(int i, byte[] bArr, int i2, int i3, int i4, int i5, int i6, int i7);
    }
}
