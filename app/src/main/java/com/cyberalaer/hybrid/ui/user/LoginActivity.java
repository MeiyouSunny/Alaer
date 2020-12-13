package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class LoginActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.login;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

}