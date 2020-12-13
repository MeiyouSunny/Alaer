package com.cyberalaer.hybrid.view.mapview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyberalaer.hybrid.R;

import java.util.List;

public class MapContainer extends ViewGroup implements MapView.OnMapStateChangedListner {
    private boolean isFirstLayout = true;
    private Context mContext;
    private int MARKER_WIDTH;
    private int MARKER_HEIGHT;
    private MapView mMapView;
    private List<Marker> mMarkers;

    public MapContainer(Context context) {
        this(context, null);
    }

    public MapContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttributes(attrs);
        initMapView();
    }

    private void initAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typeArray = mContext.obtainStyledAttributes(attrs, R.styleable.MapView);
        MARKER_WIDTH = typeArray.getDimensionPixelOffset(R.styleable.MapView_marker_width, 56);
        MARKER_HEIGHT = typeArray.getDimensionPixelOffset(R.styleable.MapView_marker_height, 24);

        MARKER_WIDTH = dip2px(getContext(), MARKER_WIDTH);
        MARKER_HEIGHT = dip2px(getContext(), MARKER_HEIGHT);
        typeArray.recycle();
    }

    private void initMapView() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mMapView = new MapView(mContext);
        mMapView.setOnMapStateChangedListner(this);
        addView(mMapView);
        mMapView.setLayoutParams(params);
    }

    @Override
    public void onChanged(RectF rectF) {
        if (mMarkers == null) {
            return;
        }
        float pWidth = rectF.width();
        float pHeight = rectF.height();
        float pLeft = rectF.left;
        float pTop = rectF.top;

        Marker marker = null;
        for (int i = 0, size = mMarkers.size(); i < size; i++) {

            marker = mMarkers.get(i);

            int left = roundValue(pLeft + pWidth * marker.getScaleX() - MARKER_WIDTH * 1f / 2);
            int top = roundValue(pTop + pHeight * marker.getScaleY() - MARKER_HEIGHT);
            int right = roundValue(pLeft + pWidth * marker.getScaleX() + MARKER_WIDTH * 1f / 2);
            int bottom = roundValue(pTop + pHeight * marker.getScaleY());

            marker.getMarkerView().layout(left, top, right, bottom);
        }
    }

    public MapView getMapView() {
        return this.mMapView;
    }

    public void setMarkers(List<Marker> markers) {
        this.mMarkers = markers;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            Object tag = child.getTag(R.id.is_marker);
            if (tag instanceof Boolean && (((Boolean) tag).booleanValue())) {
                removeView(child);
            }
        }
        initMarkers();
    }

    private void initMarkers() {
        if (mMarkers == null) {
            return;
        }

        LayoutParams params = new LayoutParams(MARKER_WIDTH, MARKER_HEIGHT);

        for (int i = 0, size = mMarkers.size(); i < size; i++) {
            Marker marker = mMarkers.get(i);
            final ImageView markerView = new ImageView(mContext);
            marker.setMarkerView(markerView);
            addView(markerView);

            markerView.setTag(R.id.is_marker, true);
            markerView.setLayoutParams(params);
            markerView.setBackgroundResource(R.drawable.bg_marker);
            markerView.setImageResource(marker.getImgSrcId());
            final int position = i;
            markerView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMarkerClickListner != null) {
                        onMarkerClickListner.onClick(markerView, position);
                    }
                }
            });
        }
    }

    private OnMarkerClickListner onMarkerClickListner;

    public void setOnMarkerClickListner(OnMarkerClickListner l) {
        this.onMarkerClickListner = l;
    }

    public interface OnMarkerClickListner {
        void onClick(View view, int position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            if (isFirstLayout) {
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
                }
            }
        }
    }

    private int roundValue(float value) {
        return Math.round(value);
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}