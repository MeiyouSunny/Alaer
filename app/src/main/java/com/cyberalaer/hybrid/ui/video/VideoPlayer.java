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

    public void setTimeListener(OnTimeChnaged timeListener) {
        this.timeListener = timeListener;
    }

    public interface OnCompleteListener {
        void onComplete();
    }

    @Override
    public void onProgress(int progress, long position, long duration) {
        super.onProgress(progress, position, duration);
        int currentSeconds = (int) (position / 1000);
        if (timeListener != null)
            timeListener.onTimeChanged(currentSeconds);
    }

    private OnTimeChnaged timeListener;

    public interface OnTimeChnaged {
        void onTimeChanged(int seconds);
    }

}
