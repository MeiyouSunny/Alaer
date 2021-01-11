package com.cyberalaer.hybrid.ui.about;

import android.view.View;

import com.alaer.lib.api.AppConfig;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAboutBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;

/**
 * 关于
 */
public class AboutActivity extends BaseTitleActivity<ActivityAboutBinding> {

    @Override
    protected int titleResId() {
        return R.string.about;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_setting);
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.userAgreement:
                WebPageActivity.start(this, AppConfig.USER_AGREEMENT, R.string.user_agreement);
                break;
        }
    }

}