package com.cyberalaer.hybrid.ui.setting;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySettngsBinding;
import com.cyberalaer.hybrid.ui.home.HomeActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

import likly.dollar.$;

/**
 * 设置
 */
public class SettingActivity extends BaseTitleActivity<ActivitySettngsBinding> {

    @Override
    protected int titleResId() {
        return R.string.system_settings;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_settngs;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            bindRoot.exit.setVisibility(View.GONE);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.exit:
                exitAccount();
                break;
            case R.id.securityCenter:
                ViewUtil.gotoActivity(this, SecurityCenterActivity.class);
                break;
        }
    }

    private void exitAccount() {
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().exitAccount(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.account_heve_exit).show();
                        UserDataUtil.instance().clearUserDatas();
                        ViewUtil.gotoActivity(getContext(), HomeActivity.class);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

}