package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserinfoBinding;

import androidx.annotation.Nullable;

/**
 * 个人中心
 */
public class UserInfoActivity extends BaseTitleActivity<ActivityUserinfoBinding> {

    @Override
    protected int titleResId() {
        return R.string.mine;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_setting);
    }
}