package llc.metaversenetwork.app.ui.produce;

import android.os.Handler;
import android.os.Looper;

import com.meiyou.mvp.MvpBinder;

import androidx.viewpager.widget.ViewPager;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivitySeedStoreBinding;

/**
 * 种子商店
 */
@MvpBinder()
public class SeedStoreActivity extends BaseTitleActivity<ActivitySeedStoreBinding> implements ViewPager.OnPageChangeListener {

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
        bindRoot.viewPager.setOnPageChangeListener(this);

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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0) {
            new Handler(Looper.getMainLooper()).postAtTime(() -> refreshMySeeds(), 800);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}