package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserMineBinding;

import androidx.annotation.Nullable;

/**
 * 个人中心
 */
public class UserMineActivity extends BaseTitleActivity<ActivityUserMineBinding> {

    @Override
    protected int titleResId() {
        return R.string.mine;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_mine;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_setting);
    }
}