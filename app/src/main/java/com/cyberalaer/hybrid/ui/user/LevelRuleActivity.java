package com.cyberalaer.hybrid.ui.user;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;

/**
 * 等级规则
 */
public class LevelRuleActivity extends BaseTitleActivity<ActivityLoginBinding> {

    @Override
    protected int titleResId() {
        return R.string.level_rule;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_level_rule;
    }

}