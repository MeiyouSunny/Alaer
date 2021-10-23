package llc.metaversenetwork.app.view.chart;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class CityBarChartManager {

    private BarChart chart;

    public CityBarChartManager(Context context, BarChart chart) {
        this.chart = chart;

        chart.setDrawBarShadow(false);
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(7);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisLineColor(Color.parseColor("#BFBFBF"));
        xAxis.setAxisLineWidth(1.5F);
        xAxis.setLabelRotationAngle(-30);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(7, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisLineColor(Color.parseColor("#BFBFBF"));
        leftAxis.setDrawZeroLine(false);
        leftAxis.setGridColor(Color.parseColor("#FFBFBFBF"));
        leftAxis.setAxisLineWidth(1.5F);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        chart.getLegend().setEnabled(false);

        ChartMarkerView mv = new ChartMarkerView(context);
        mv.setChartView(chart);
        chart.setMarker(mv);
    }

    public void show(List<String> dateList, List<Integer> valueList) {
        chart.getXAxis().setValueFormatter(new CItyXBarValueFormatter(dateList));

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = 0; i < dateList.size(); i++) {
            values.add(new BarEntry(i, valueList.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(values, "");
        barDataSet.setDrawIcons(false);
        barDataSet.setColor(Color.parseColor("#52C883"));
        barDataSet.setHighLightAlpha(30);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData barData = new BarData(dataSets);
        barData.setBarWidth(0.3F);
        barData.setDrawValues(false);


        chart.setData(barData);
//        chart.invalidate();
        chart.highlightValue(0, 0);
    }

}
