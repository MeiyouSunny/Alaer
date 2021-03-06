package com.cyberalaer.hybrid.ui.government;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;
import com.cyberalaer.hybrid.ui.auth.AuthActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 实名认证
 */
public class RealNameAuthActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_real_name_auth;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.authByAlipy:
                ViewUtil.gotoActivity(this, AuthActivity.class);
                break;
        }
    }

}