package com.cyberalaer.hybrid.view.mapview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageView;

public class MapView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener {

    private boolean isPicLoaded = false;
    float SCALE_MIN = 0.9f;
    float SCALE_ADAPTIVE = 1.0f;
    float SCALE_MID = 1.5f;
    float SCALE_MAX = 2.0f;
    private Matrix mScaleMatrix;
    private ScaleGestureDetector mScaleGestureDetector;
    private GestureDetector mGestureDetector;
    private boolean isAutoScaling = false;
    private int mTouchSlop;

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = initGestureDetector(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private GestureDetector initGestureDetector(Context context) {
        GestureDetector.SimpleOnGestureListener listner = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (!isAutoScaling) {
                    isAutoScaling = true;
                    float x = e.getX();
                    float y = e.getY();
                    float scale = getDrawableScale();
                    if (scale < SCALE_MID) {
                        post(new AutoScaleTask(SCALE_MID, x, y));
                    } else if (scale >= SCALE_MID && scale < SCALE_MAX) {
                        post(new AutoScaleTask(SCALE_MAX, x, y));
                    } else if (scale == SCALE_MAX) {
                        post(new AutoScaleTask(SCALE_ADAPTIVE, x, y));
                    } else {
                        isAutoScaling = false;
                    }
                }
                return super.onDoubleTap(e);
            }
        };
        return new GestureDetector(context, listner);
    }

    @Override
    public void onGlobalLayout() {
        if (!isPicLoaded) {
            Drawable drawable = getDrawable();
            if (null == drawable) {
                return;
            }
            isPicLoaded = true;
            int iWidth = drawable.getIntrinsicWidth();
            int iHeight = drawable.getIntrinsicHeight();

            int width = getWidth();
            int height = getHeight();

            if (iWidth >= width && iHeight <= height) {
                SCALE_ADAPTIVE = height * 1f / iHeight;
            } else if (iWidth <= width && iHeight >= height) {
                SCALE_ADAPTIVE = width * 1f / iWidth;
            } else if (iWidth >= width && iHeight >= height || iWidth <= width && iHeight <= height) {
                SCALE_ADAPTIVE = Math.max(width * 1f / iWidth, height * 1f / iHeight);
            }

            mScaleMatrix.postTranslate((width - iWidth) * 1f / 2, (height - iHeight) * 1f / 2);
            mScaleMatrix.postScale(SCALE_ADAPTIVE, SCALE_ADAPTIVE, width * 1f / 2, height * 1f / 2);
            setImageMatrix(mScaleMatrix);
            onChangedListner.onChanged(getMatrixRect());

            SCALE_MAX *= SCALE_ADAPTIVE;
            SCALE_MID *= SCALE_ADAPTIVE;
            SCALE_MIN *= SCALE_ADAPTIVE;
        }
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (getDrawable() == null) {
            return true;
        }
        float scaleFactor = detector.getScaleFactor();
        float scale = getDrawableScale();
        if (scale <= SCALE_MAX && scaleFactor < 1 || scale >= SCALE_MIN && scaleFactor > 1) {
            if (scale * scaleFactor < SCALE_MIN && scaleFactor < 1) {
                scaleFactor = SCALE_MIN / scale;
            }
            if (scale * scaleFactor > SCALE_MAX && scaleFactor > 1) {
                scaleFactor = SCALE_MAX / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            checkBoderAndCenter();

            setImageMatrix(mScaleMatrix);
            onChangedListner.onChanged(getMatrixRect());
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Drawable drawable = getDrawable();
        if (drawable == null) return;
        float scale = getDrawableScale();
        if (scale < SCALE_ADAPTIVE) {
            post(new AutoScaleTask(SCALE_ADAPTIVE, getWidth() * 1f / 2, getHeight() * 1f));
        }
    }

    private class AutoScaleTask implements Runnable {
        float targetScale;
        float x;
        float y;
        static final float TMP_AMPLIFY = 1.06f;
        static final float TMP_SHRINK = 0.94f;
        float tmpScale = 1f;

        public AutoScaleTask(float targetScale, float x, float y) {
            this.targetScale = targetScale;
            this.x = x;
            this.y = y;
            if (getDrawableScale() < targetScale) {
                tmpScale = TMP_AMPLIFY;
            } else {
                tmpScale = TMP_SHRINK;
            }
        }

        @Override
        public void run() {
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBoderAndCenter();
            setImageMatrix(mScaleMatrix);
            onChangedListner.onChanged(getMatrixRect());
            float scale = getDrawableScale();
            if (tmpScale > 1 && scale < targetScale || scale > targetScale && tmpScale < 1) {
                post(this);
            } else {
                tmpScale = targetScale / scale;
                mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
                checkBoderAndCenter();
                setImageMatrix(mScaleMatrix);
                onChangedListner.onChanged(getMatrixRect());
                isAutoScaling = false;
            }
        }
    }

    private void moveByTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                RectF rect = getMatrixRect();
                if (rect.width() <= getWidth() && rect.height() <= getHeight()) {
                    return;
                }
                int x = 0;
                int y = 0;
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    x += event.getX(i);
                    y += event.getY(i);
                }
                x /= pointerCount;
                y /= pointerCount;

                if (mLastPointCount != pointerCount) {
                    mLastX = x;
                    mLastY = y;
                    mLastPointCount = pointerCount;
                }
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (isCanDrag(deltaX, deltaY)) {

                    if (rect.width() <= getWidth()) {
                        deltaX = 0;
                    }
                    if (rect.height() <= getHeight()) {
                        deltaY = 0;
                    }
                    mScaleMatrix.postTranslate(deltaX, deltaY);
                    checkBoderAndCenter();
                    setImageMatrix(mScaleMatrix);
                    onChangedListner.onChanged(getMatrixRect());
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mLastPointCount = 0;
                break;
        }
    }

    int mLastX;
    int mLastY;
    int mLastPointCount;

    private void checkBoderAndCenter() {
        RectF rect = getMatrixRect();
        int width = getWidth();
        int height = getHeight();

        float deltaX = 0;
        float deltaY = 0;

        if (rect.width() >= width) {
            if (rect.left > 0) {
                deltaX = -rect.left;
            }
            if (rect.right < width) {
                deltaX = width - rect.right;
            }
        }
        if (rect.height() >= height) {
            if (rect.top > 0) {
                deltaY = -rect.top;
            }
            if (rect.bottom < height) {
                deltaY = height - rect.bottom;
            }
        }

        if (rect.width() < width) {
            deltaX = width * 1f / 2 - rect.right + rect.width() * 1f / 2;
        }

        if (rect.height() < height) {
            deltaY = height * 1f / 2 - rect.bottom + rect.height() * 1f / 2;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    private RectF getMatrixRect() {
        RectF rect = new RectF();
        Drawable drawable = getDrawable();
        if (drawable != null) {
            rect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        mScaleMatrix.mapRect(rect);
        return rect;
    }

    private float getDrawableScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mScaleGestureDetector != null) {
            mScaleGestureDetector.onTouchEvent(event);
        }
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        if (!isAutoScaling) {
            moveByTouchEvent(event);
        }
        return true;
    }

    private boolean isCanDrag(int deltaX, int deltaY) {
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY) >= mTouchSlop;
    }

    private OnMapStateChangedListner onChangedListner;

    public void setOnMapStateChangedListner(OnMapStateChangedListner l) {
        onChangedListner = l;
    }

    public interface OnMapStateChangedListner {
        void onChanged(RectF rectF);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

}
