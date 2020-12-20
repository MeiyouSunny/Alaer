package com.cyberalaer.hybrid.ui.discover;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 发现
 */
public class DiscoverActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.discover;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_discover;
    }

}