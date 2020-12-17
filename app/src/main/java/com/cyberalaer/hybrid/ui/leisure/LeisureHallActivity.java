package com.cyberalaer.hybrid.ui.leisure;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 休闲大厅
 */
public class LeisureHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.leisure_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_leisure;
    }

}