package com.cyberalaer.hybrid.ui.produce;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySeedStoreBinding;
import com.meiyou.mvp.MvpBinder;

/**
 * 种子商店
 */
@MvpBinder()
public class SeedStoreActivity extends BaseTitleActivity<ActivitySeedStoreBinding> {

    TabPagerAdapter mTabAdapter;

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
        int index = getIntent().getIntExtra("index", 0);

        mTabAdapter = new TabPagerAdapter(this, getSupportFragmentManager(),
                getResources().getStringArray(R.array.produce_base_tabs), claimNewbieMiner);
        bindRoot.viewPager.setOffscreenPageLimit(3);
        bindRoot.viewPager.setAdapter(mTabAdapter);
        bindRoot.tabs.setupWithViewPager(bindRoot.viewPager);

        if (index != 0)
            bindRoot.viewPager.setCurrentItem(index);
    }

    // 刷新我的树苗
    public void refreshMySeeds() {
        ((SeedMineFragment) mTabAdapter.getItem(0)).refresh();
    }

    public void showPage(int index) {
        bindRoot.viewPager.setCurrentItem(index);
    }

}