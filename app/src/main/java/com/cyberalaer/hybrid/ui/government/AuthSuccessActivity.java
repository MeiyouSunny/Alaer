package com.cyberalaer.hybrid.ui.government;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAuthSuccessBinding;
import com.cyberalaer.hybrid.ui.share.ShareActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 实名认证完成
 */
public class AuthSuccessActivity extends BaseTitleActivity<ActivityAuthSuccessBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_auth_success;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.share:
                ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
        }
    }

}