package com.cyberalaer.hybrid.ui.education;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 教育大厅
 */
public class EducationHallActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.education_hall;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_education;
    }

}