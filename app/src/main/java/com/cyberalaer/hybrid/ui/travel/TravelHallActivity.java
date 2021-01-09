package com.cyberalaer.hybrid.ui.travel;

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
        ViewUtil.showImage(getApplicationContext(), bindRoot.player1.posterImageView, "https://app.tokensky.cn/ale/video/tourism-202011-poster.png");
        bindRoot.player1.setUp("https://app.tokensky.cn/ale/video/tourism-202011.mp4", "");
        ViewUtil.showImage(getApplicationContext(), bindRoot.player2.posterImageView, "https://app.tokensky.cn/ale/video/tourism-2-poster.png");
        bindRoot.player2.setUp("https://app.tokensky.cn/ale/video/tourism-2.mp4", "");

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
        ;
    }

}