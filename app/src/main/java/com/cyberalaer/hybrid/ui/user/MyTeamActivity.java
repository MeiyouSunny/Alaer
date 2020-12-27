package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityMyTeamBinding;

import androidx.viewpager.widget.ViewPager;

/**
 * 我的团队
 */
public class MyTeamActivity extends BaseTitleActivity<ActivityMyTeamBinding> {

    @Override
    protected int titleResId() {
        return R.string.my_team;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_my_team;
    }

    @Override
    public void onViewCreated() {
        TabActiveDetailAdapter sectionsPagerAdapter = new TabActiveDetailAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.my_team_tabs));
        ViewPager viewPager = findViewById(R.id.view_pager);
        bindRoot.viewPager.setAdapter(sectionsPagerAdapter);
        bindRoot.tabs.setupWithViewPager(viewPager);
    }

}