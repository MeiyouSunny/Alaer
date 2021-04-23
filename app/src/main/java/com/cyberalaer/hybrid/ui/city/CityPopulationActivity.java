package com.cyberalaer.hybrid.ui.city;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CityChartData;
import com.alaer.lib.api.bean.CityStatistic;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityCityPopulationBinding;
import com.cyberalaer.hybrid.view.chart.CityBarChartManager;

import java.util.List;

/**
 * 城市人数
 */
public class CityPopulationActivity extends BaseTitleActivity<ActivityCityPopulationBinding> {

    UserData userData;

    @Override
    protected int titleResId() {
        return R.string.city_population;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_population;
    }

    @Override
    public void click(View view) {
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        getWeekData();
    }

    private void getWeekData() {
        userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().cityWeekData(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<CityChartData>() {
                    @Override
                    public void onResponse(CityChartData data) {
                        if (data != null) {
                            showChart(data.date, data.people);
                        }
                    }
                });

        ApiUtil.apiService().cityStatistic(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<CityStatistic>() {
                    @Override
                    public void onResponse(CityStatistic data) {
                        bindRoot.setStatistic(data);
                    }
                });
    }

    private void showChart(List<String> dateList, List<Integer> peopleList) {
        CityBarChartManager chartManager = new CityBarChartManager(this, bindRoot.barChart);
        chartManager.show(dateList, peopleList);
    }

}