package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.Balance;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityBuildScoreBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.NumberUtils;

/**
 * 建设工分
 */
public class BuildScoreActivity extends BaseTitleActivity<ActivityBuildScoreBinding> {

    @Override
    protected int titleResId() {
        return R.string.build_integral;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_build_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

        Balance balance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(balance);
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.BUILD_SCORE_EXPLAIN, R.string.build_score_explain);
    }

}