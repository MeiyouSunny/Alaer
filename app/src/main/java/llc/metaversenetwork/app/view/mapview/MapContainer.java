package llc.metaversenetwork.app.view.mapview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import llc.metaversenetwork.app.R;

public class MapContainer extends ViewGroup implements MapView.OnMapStateChangedListner {
    private boolean isFirstLayout = true;
    private Context mContext;
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

            int left = roundValue(pLeft + pWidth * marker.getScaleX() - marker.getMarkerView().getMeasuredWidth() * 1f / 2);
            int top = roundValue(pTop + pHeight * marker.getScaleY() - marker.getMarkerView().getMeasuredHeight());
            int right = roundValue(pLeft + pWidth * marker.getScaleX() + marker.getMarkerView().getMeasuredWidth() * 1f / 2);
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

//        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, MARKER_HEIGHT);

        for (int i = 0, size = mMarkers.size(); i < size; i++) {
            Marker marker = mMarkers.get(i);
            final View markerView = LayoutInflater.from(getContext()).inflate(R.layout.map_marker_, null);
//            markerView.setPadding(16, 6, 16, 6);
            markerView.setTag(R.id.is_marker, true);
//            markerView.setLayoutParams(params);
            ((TextView) markerView.findViewById(R.id.name)).setText(marker.getText());
            marker.setMarkerView(markerView);
            addView(markerView);

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