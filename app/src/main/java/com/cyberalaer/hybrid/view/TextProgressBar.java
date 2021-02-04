package com.cyberalaer.hybrid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.util.TimeUtil;

import androidx.annotation.NonNull;

public class TextProgressBar extends ProgressBar {
    private String mTextShow;
    private int mTextColor = Color.WHITE;
    private float mTextSize = 12;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    // 当前阶段的开始时间/结束时间/当前时间 毫秒值
    private long timeStart, timeEnd, timeNow;

    private ProgressChange mListener;

    public TextProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TextProgressBar, defStyle, 0);

        mTextShow = a.getString(R.styleable.TextProgressBar_text);
        mTextColor = a.getColor(R.styleable.TextProgressBar_textColor, mTextColor);
        mTextSize = a.getDimension(R.styleable.TextProgressBar_textSize, mTextSize);

        a.recycle();
        initPaint();
        invalidateTextPaintAndMeasurements();
    }

    private void initPaint() {
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void invalidateTextPaintAndMeasurements() {
        if (TextUtils.isEmpty(mTextShow))
            return;
        mTextWidth = mTextPaint.measureText(mTextShow);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    public void setTimes(long timeStart, long timeEnd, long timeNow) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeNow = timeNow;

        removeMsg(false);
        showProgressAndTime();
    }

    private void showProgressAndTime() {
        if (timeNow >= timeEnd) {
            // 如果时间已经结束
            setProgress(100);
            String timeText = getResources().getString(R.string.time_remain, "00:00:00");
            setTextShow(timeText);

            if (mListener != null)
                mListener.onProgressComplete();
            return;
        }

        long timeRemain = timeEnd - timeNow;
        String timeText = getResources().getString(R.string.time_remain, TimeUtil.parseMillesToTimeString(timeRemain));
        setTextShow(timeText);

        int progress = (int) (((timeNow - timeStart) / (float) (timeEnd - timeStart)) * 100);
        setProgress(progress);

        invalidate();
        // 倒计时
        handler.sendEmptyMessageDelayed(MSG_TYPE_PROGRESS, 1000);
    }

    private final static int MSG_TYPE_PROGRESS = 1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_TYPE_PROGRESS) {
                timeNow += 1000L;
                showProgressAndTime();
            }

        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(mTextShow))
            return;
        if (mTextPaint == null) {
            initPaint();
        }
        Rect rect = new Rect();
        mTextPaint.getTextBounds(mTextShow, 0, mTextShow.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();

        canvas.drawText(mTextShow,
                80,
                y,
                mTextPaint);
    }

    public void setTextShow(String textShow) {
        mTextShow = textShow;
        invalidateTextPaintAndMeasurements();
    }

    private void removeMsg(boolean setNull) {
        if (handler != null && handler.hasMessages(MSG_TYPE_PROGRESS)) {
            handler.removeMessages(MSG_TYPE_PROGRESS);
            if (setNull)
                handler = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeMsg(true);
    }

    public void setProgressListener(ProgressChange listener) {
        mListener = listener;
    }

    public interface ProgressChange {
        void onProgressComplete();
    }

}