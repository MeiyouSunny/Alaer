package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;

@MvpBinder(
)
public class LoginActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.enter;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}