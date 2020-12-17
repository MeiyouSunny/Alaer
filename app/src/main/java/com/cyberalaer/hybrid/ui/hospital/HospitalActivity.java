package com.cyberalaer.hybrid.ui.hospital;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 人民医院
 */
public class HospitalActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.people_hospital;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_hospital;
    }

}