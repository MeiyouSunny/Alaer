package com.cyberalaer.hybrid.ui.produce;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityProductionBaseBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.viewpager.widget.ViewPager;

/**
 * 生产基地
 */
@MvpBinder()
public class ProductionBaseActivity extends BaseTitleActivity<ActivityProductionBaseBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_production_base;
    }

    @Override
    protected int titleResId() {
        return R.string.produce_base;
    }

    @Override
    public void onViewCreated() {
        TabPagerAdapter sectionsPagerAdapter = new TabPagerAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.produce_base_tabs));
        ViewPager viewPager = findViewById(R.id.view_pager);
        bindRoot.viewPager.setAdapter(sectionsPagerAdapter);
        bindRoot.tabs.setupWithViewPager(viewPager);
    }

}