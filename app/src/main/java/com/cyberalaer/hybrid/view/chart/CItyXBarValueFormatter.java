package com.cyberalaer.hybrid.view.chart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

public class CItyXBarValueFormatter implements IAxisValueFormatter {

    private List<String> dateList;

    public CItyXBarValueFormatter(List<String> dateList) {
        this.dateList = dateList;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if ((int) value < dateList.size())
            return dateList.get((int) value);
        return "";
    }

}
