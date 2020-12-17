package com.cyberalaer.hybrid.ui.government;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityGovernmentBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 政务大厅
 */
public class GovernmentHallActivity extends BaseTitleActivity<ActivityGovernmentBinding> {

    @Override
    protected int titleResId() {
        return R.string.government_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_government;
    }

    @Override
    public void click(View view) {
        if(view.getId() == R.id.realNameAuth) {
            ViewUtil.gotoActivity(this, RealNameAuthActivity.class);
        }
    }

}