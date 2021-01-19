package com.cyberalaer.hybrid.ui.education;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityLoginBinding;
import com.cyberalaer.hybrid.ui.user.UserMineActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

import likly.dollar.$;

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

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.shortVideo:
            case R.id.film:
            case R.id.novel:
                $.toast().text(R.string.will_open_soon).show();
                break;
        }
    }

}