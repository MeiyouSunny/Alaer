package com.cyberalaer.hybrid.ui.produce;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySeedStoreBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.viewpager.widget.ViewPager;

/**
 * 种子商店
 */
@MvpBinder()
public class SeedStoreActivity extends BaseTitleActivity<ActivitySeedStoreBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_seed_store;
    }

    @Override
    protected int titleResId() {
        return R.string.seed_store;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        boolean claimNewbieMiner = getIntent().getBooleanExtra("claimNewbieMiner", false);

        TabPagerAdapter sectionsPagerAdapter = new TabPagerAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.produce_base_tabs), claimNewbieMiner);
        ViewPager viewPager = findViewById(R.id.view_pager);
        bindRoot.viewPager.setAdapter(sectionsPagerAdapter);
        bindRoot.tabs.setupWithViewPager(viewPager);
    }

}