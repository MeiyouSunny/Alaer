package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityBuildScoreBinding;

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
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_question);
    }

}