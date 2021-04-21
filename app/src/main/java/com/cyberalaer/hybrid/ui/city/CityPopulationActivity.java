package com.cyberalaer.hybrid.ui.city;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAboutBinding;

/**
 * 城市人数
 */
public class CityPopulationActivity extends BaseTitleActivity<ActivityAboutBinding> {

    @Override
    protected int titleResId() {
        return R.string.city_population;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_population;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

    @Override
    public void click(View view) {
    }

}