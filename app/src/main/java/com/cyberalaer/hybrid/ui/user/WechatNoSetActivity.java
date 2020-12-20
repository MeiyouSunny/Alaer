package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserMineBinding;

/**
 * 微信号设置
 */
public class WechatNoSetActivity extends BaseTitleActivity<ActivityUserMineBinding> {

    @Override
    protected int titleResId() {
        return R.string.wechat_no_set;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wechat_no_set;
    }

}