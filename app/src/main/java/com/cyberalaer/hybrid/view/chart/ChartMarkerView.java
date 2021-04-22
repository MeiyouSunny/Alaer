
package com.cyberalaer.hybrid.view.chart;

import android.content.Context;
import android.widget.TextView;

import com.cyberalaer.hybrid.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class ChartMarkerView extends MarkerView {

    private final TextView tvContent;

    public ChartMarkerView(Context context) {
        super(context, R.layout.city_chat_marker_view);

        tvContent = findViewById(R.id.value);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText(String.valueOf(e.getY()));

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight() - 10);
    }

}
