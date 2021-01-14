package com.cyberalaer.hybrid.ui.user;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.annotation.Nullable;

/**
 * 用户信息
 */
public class UserInfoActivity extends BaseTitleActivity<ActivityUserInfoBinding> {

    private TeamDetail mInviterInfo;

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

        TeamDetail teamDetail = UserDataUtil.instance().getTeamDetail();
        if (teamDetail != null) {
            bindRoot.setUser(teamDetail);
        }

        ApiUtil.apiService().getFollowInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail response) {
                        mInviterInfo = response;
                        bindRoot.setFollow(response);
                    }
                });
    }

    private void showUserInfo(TeamDetail userInfo) {
        ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, userInfo.avatar);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.setWechat:
                SetProfileActivity.start(this, SetProfileActivity.WECHAT);
                break;
            case R.id.setInvitateCode:
                SetProfileActivity.start(this, SetProfileActivity.INVITATE_CODE);
                break;
            case R.id.setNikeName:
                SetProfileActivity.start(this, SetProfileActivity.NIKE_NAME);
                break;
            case R.id.inviterInfo:
                goToInviterInfo();
                break;
        }
    }

    private void goToInviterInfo() {
        if (mInviterInfo == null)
            return;
        Bundle data = new Bundle();
        data.putSerializable("inviter", mInviterInfo);
        ViewUtil.gotoActivity(getContext(), InviterInfoActivity.class, data);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SetProfileActivity.REQUST_CODE && resultCode == RESULT_OK) {
            getData();
        }
    }

}