package com.cyberalaer.hybrid.ui.government;

import android.view.View;

import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityGovernmentBinding;
import com.cyberalaer.hybrid.ui.user.UserMineActivity;
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
        switch (view.getId()) {
            case R.id.auth:
                if (UserDataUtil.instance().isAuthed())
                    ViewUtil.gotoActivity(this, AuthSuccessActivity.class);
                else
                    ViewUtil.gotoActivity(this, RealNameAuthActivity.class);
                break;
            case R.id.mine:
                ViewUtil.gotoActivity(this, UserMineActivity.class);
                break;
        }
    }

}