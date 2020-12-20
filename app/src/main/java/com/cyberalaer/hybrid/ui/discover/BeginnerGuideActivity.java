package com.cyberalaer.hybrid.ui.discover;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 新手指南
 */
public class BeginnerGuideActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.beginner_guide;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_beginner_guide;
    }

}