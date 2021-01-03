package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserInfoBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseTitleActivity<ActivityUserInfoBinding> {

    @Override
    protected int titleResId() {
        return R.string.user_info;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        getData();
    }

    private void getData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail teamDetail) {
                        showUserInfo(teamDetail);
                        UserDataUtil.instance().setTeamDetail(teamDetail);
                    }
                });

        ApiUtil.apiService().getFollowInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail response) {
                        showInvitate(response);
                    }
                });
    }

    private void showInvitate(TeamDetail userData) {
        ViewUtil.setText(bindRoot.invatePerson, userData.name);
    }

    private void showUserInfo(TeamDetail userInfo) {
        ViewUtil.setText(bindRoot.uid, String.valueOf(userInfo.uid));
        ViewUtil.setText(bindRoot.name, String.valueOf(userInfo.name));
        ViewUtil.setText(bindRoot.invateCode, String.valueOf(userInfo.code));
        ViewUtil.setText(bindRoot.wechatNo, String.valueOf(userInfo.wechat));
        ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, userInfo.avatar);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.setWechat:
                goToSetWechat();
                break;
        }
    }

    private void goToSetWechat() {
        ViewUtil.gotoActivity(getContext(), WechatNoSetActivity.class);
    }

}