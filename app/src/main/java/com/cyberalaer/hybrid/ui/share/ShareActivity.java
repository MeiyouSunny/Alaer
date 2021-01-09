package com.cyberalaer.hybrid.ui.share;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityShareBinding;
import com.meiyou.mvp.MvpBinder;

/**
 * 邀请好友
 */
@MvpBinder()
public class ShareActivity extends BaseTitleActivity<ActivityShareBinding> {

    @Override
    protected int layoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected int titleResId() {
        return R.string.invitate_friend;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

    }

}