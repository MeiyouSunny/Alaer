package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityFruitBillBinding;

/**
 * 果实支出
 */
public class FruitBillActivity extends BaseTitleActivity<ActivityFruitBillBinding> {

    @Override
    protected int titleResId() {
        return R.string.fruit_bill;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_fruit_bill;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_question);

//        TabActiveDetailAdapter sectionsPagerAdapter = new TabActiveDetailAdapter(this, getSupportFragmentManager(),
//                getResources().getStringArray(R.array.fruit_bill_tabs));
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        bindRoot.viewPager.setAdapter(sectionsPagerAdapter);
//        bindRoot.tabs.setupWithViewPager(viewPager);
    }

}