package com.cyberalaer.hybrid.ui.setting;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AccessPointInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAccessPointBinding;

/**
 * 接入点
 */
public class AccessPointActivity extends BaseTitleActivity<ActivityAccessPointBinding> {

    @Override
    protected int titleResId() {
        return R.string.access_point;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_access_point;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setRightTitleText(R.string.refresh);
        getAccessPointInfo();
    }

    @Override
    public void click(View view) {
    }

    // 刷新
    protected void onRightTitleClick() {
        getAccessPointInfo();
    }

    private void getAccessPointInfo() {
        long timeStart = System.currentTimeMillis();
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().accessPoint(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<AccessPointInfo>() {
                    @Override
                    public void onResponse(AccessPointInfo accessPointInfo) {
                        long speed = accessPointInfo.timestamp - timeStart;
                        bindRoot.speed.setText(speed + "ms");
                    }
                });
    }

}