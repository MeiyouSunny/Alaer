package llc.metaversenetwork.app.view.mapview;

import android.view.View;

public class Marker {
    private float scaleX;
    private float scaleY;
    private View markerView;
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

    public void setMarkerView(View markerView) {
        this.markerView = markerView;
    }

    public int getText() {
        return textResId;
    }

    public void setText(int imgSrcId) {
        this.textResId = imgSrcId;
    }

    public View getMarkerView() {
        return markerView;
    }
}
