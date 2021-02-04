package com.cyberalaer.hybrid.view;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FullyLinearLayoutManager extends LinearLayoutManager {

    private int targetNum;

    public FullyLinearLayoutManager(Context context, int targetNum) {
        super(context);
        this.targetNum = targetNum;
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        int count = state.getItemCount();
        if (count > 0) {
            if (count > targetNum) {
                count = targetNum;
            }
            int realHeight = 0;
            int realWidth = 0;
            for (int i = 0; i < count; i++) {
                View view = recycler.getViewForPosition(0);
                if (view != null) {
                    measureChild(view, widthSpec, heightSpec);
                    int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    int measuredHeight = view.getMeasuredHeight();
                    realWidth = realWidth > measuredWidth ? realWidth : measuredWidth;
                    realHeight = realHeight + measuredHeight;
                }
            }
            setMeasuredDimension(realWidth, realHeight);
        } else {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
        }
    }

}
