package com.cyberalaer.hybrid.ui.welcom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityGuideBinding;
import com.cyberalaer.hybrid.ui.home.HomeActivity;
import com.cyberalaer.hybrid.ui.user.LoginActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import likly.dollar.$;

public class GuideActivity extends BaseViewBindActivity<ActivityGuideBinding> implements ViewPager.OnPageChangeListener, View.OnClickListener {

    @Override
    protected int layoutId() {
        return R.layout.activity_guide;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        boolean needShowGuide = $.config().getBoolean("showGuide", true);
        if (needShowGuide)
            $.config().putBoolean("showGuide", false);
        if (!needShowGuide) {
            ViewUtil.gotoActivity(this, LoginActivity.class);
            return;
        }

        setGuideView();
    }

    private void setGuideView() {
        List<View> views = new ArrayList<View>();
        final LayoutInflater layoutInflater = getLayoutInflater();
        View guideView1 = layoutInflater.inflate(R.layout.layout_welcom_1, null);
        View guideView2 = layoutInflater.inflate(R.layout.layout_welcom_2, null);
        View guideView3 = layoutInflater.inflate(R.layout.layout_welcom_3, null);
        views.add(guideView1);
        views.add(guideView2);
        views.add(guideView3);
        bindRoot.viewPager.setAdapter(new GuideAdapter(views));

        bindRoot.viewPager.setOnPageChangeListener(this);
        guideView3.findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ViewUtil.gotoActivity(this, HomeActivity.class);
        finish();
    }

    class GuideAdapter extends PagerAdapter {

        private List<View> views;

        public GuideAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views != null ? views.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bindRoot.setIndicatorIndex(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

}