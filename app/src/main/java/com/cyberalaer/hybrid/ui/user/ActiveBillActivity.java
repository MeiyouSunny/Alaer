package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityActiveDetailBinding;

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
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_question);
    }

}