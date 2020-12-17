package com.cyberalaer.hybrid.ui.travel;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 旅游大厅
 */
public class TravelHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.travel_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_travel;
    }

}