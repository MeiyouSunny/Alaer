package com.cyberalaer.hybrid.ui.shopping;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 数字商城
 */
public class DigitalMallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.digital_mall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_digital_mall;
    }

}