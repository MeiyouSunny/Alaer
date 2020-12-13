package com.cyberalaer.hybrid.view.mapview;

import android.widget.ImageView;

public class Marker {
    private float scaleX;
    private float scaleY;
    private ImageView markerView;
    private int imgSrcId;

    public Marker() {
    }

    public Marker(float scaleX, float scaleY, int imgSrcId) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.imgSrcId = imgSrcId;
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

    public void setMarkerView(ImageView markerView) {
        this.markerView = markerView;
    }

    public int getImgSrcId() {
        return imgSrcId;
    }

    public void setImgSrcId(int imgSrcId) {
        this.imgSrcId = imgSrcId;
    }

    public ImageView getMarkerView() {
        return markerView;
    }
}
