package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserMineBinding;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseTitleActivity<ActivityUserMineBinding> {

    @Override
    protected int titleResId() {
        return R.string.user_info;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_info;
    }

}