package llc.metaversenetwork.app.view.mapview;

import android.widget.TextView;

public class Marker {
    private float scaleX;
    private float scaleY;
    private TextView markerView;
    private int textResId;

    public Marker() {
    }

    public Marker(float scaleX, float scaleY, int text) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.textResId = text;
    }

    public float getScaleX() {
        return scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void setMarkerView(TextView markerView) {
        this.markerView = markerView;
    }

    public int getText() {
        return textResId;
    }

    public void setText(int imgSrcId) {
        this.textResId = imgSrcId;
    }

    public TextView getMarkerView() {
        return markerView;
    }
}
