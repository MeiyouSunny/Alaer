package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityActiveDetailBinding;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 果实支出
 */
public class FruitBIllActivity extends BaseTitleActivity<ActivityActiveDetailBinding> {

    @Override
    protected int titleResId() {
        return R.string.fruit_bill;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_active_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_question);
    }

    @Override
    public void onViewCreated() {
        TabActiveDetailAdapter sectionsPagerAdapter = new TabActiveDetailAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.fruit_bill_tabs));
        ViewPager viewPager = findViewById(R.id.view_pager);
        bindRoot.viewPager.setAdapter(sectionsPagerAdapter);
        bindRoot.tabs.setupWithViewPager(viewPager);
    }

}