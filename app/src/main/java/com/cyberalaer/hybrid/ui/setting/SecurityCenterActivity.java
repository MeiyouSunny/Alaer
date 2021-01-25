package com.cyberalaer.hybrid.ui.setting;

import android.view.View;

import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySecurityCenterBinding;
import com.cyberalaer.hybrid.ui.user.SecondPwdSetActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 安全中心
 */
public class SecurityCenterActivity extends BaseTitleActivity<ActivitySecurityCenterBinding> {

    @Override
    protected int titleResId() {
        return R.string.security_center;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_security_center;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData != null)
            bindRoot.phone.setText(userData.phone);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.secondPwd:
                ViewUtil.gotoActivity(getContext(), SecondPwdSetActivity.class);
                break;
        }
    }

}