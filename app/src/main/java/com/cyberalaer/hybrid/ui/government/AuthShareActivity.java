package com.cyberalaer.hybrid.ui.government;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 实名认证完成分享
 */
public class AuthShareActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.real_name_auth;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_real_name_auth_success;
    }

}