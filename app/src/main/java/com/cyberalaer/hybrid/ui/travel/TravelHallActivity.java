package com.cyberalaer.hybrid.ui.travel;

import com.alaer.lib.api.AppConfig;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityTravelBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import cn.jzvd.Jzvd;

/**
 * 旅游大厅
 */
public class TravelHallActivity extends BaseTitleActivity<ActivityTravelBinding> {

    @Override
    protected int titleResId() {
        return R.string.travel_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_travel;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        initData();
    }

    private void initData() {
        ViewUtil.showImage(getApplicationContext(), bindRoot.player1.posterImageView, AppConfig.TRAVEL_VIDEO_PIC1);
        bindRoot.player1.setUp(AppConfig.TRAVEL_VIDEO1, "");
        ViewUtil.showImage(getApplicationContext(), bindRoot.player2.posterImageView, AppConfig.TRAVEL_VIDEO_PIC2);
        bindRoot.player2.setUp(AppConfig.TRAVEL_VIDEO2, "");

    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Jzvd.releaseAllVideos();
    }

}