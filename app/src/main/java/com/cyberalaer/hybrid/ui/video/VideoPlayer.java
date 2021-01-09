package com.cyberalaer.hybrid.ui.video;

import android.content.Context;
import android.util.AttributeSet;

import cn.jzvd.JzvdStd;

public class VideoPlayer extends JzvdStd {

    private OnCompleteListener onCompleteListener;

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onCompletion() {
        super.onCompletion();
        if (onCompleteListener != null) {
            onCompleteListener.onComplete();
        }
    }

    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    public interface OnCompleteListener {
        void onComplete();
    }

}
