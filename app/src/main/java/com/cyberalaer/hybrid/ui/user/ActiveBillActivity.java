package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.TeamInfo;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityActiveDetailBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;

/**
 * 树苗活跃度明细
 */
public class ActiveBillActivity extends BaseTitleActivity<ActivityActiveDetailBinding> {

    @Override
    protected int titleResId() {
        return R.string.active_detail;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_active_detail;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        TeamInfo data = (TeamInfo) getIntent().getSerializableExtra("teamInfo");
        bindRoot.setData(data);
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.BUILD_SCORE_EXPLAIN, R.string.activity_explain);
    }

}