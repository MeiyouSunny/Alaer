package com.cyberalaer.hybrid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.cyberalaer.hybrid.R;

public class TextProgressBar extends ProgressBar {
    private String mTextShow;
    private int mTextColor = Color.WHITE;
    private float mTextSize = 12;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

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

        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextWidth = mTextPaint.measureText(mTextShow);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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

}