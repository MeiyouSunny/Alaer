package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.TeamInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserMineBinding;
import com.cyberalaer.hybrid.ui.share.ShareActivity;
import com.cyberalaer.hybrid.util.NumberUtils;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 个人中心
 */
public class UserMineActivity extends BaseTitleActivity<ActivityUserMineBinding> {

    Balance mBalance;
    TeamInfo mTeamInfo;

    @Override
    protected int titleResId() {
        return R.string.mine;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_user_mine;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightVisible(true);
        setTitleRightIcon(R.drawable.ic_setting);
        initData();
    }

    private void initData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        showUserInfo();

        ApiUtil.apiService().getBalance(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<Balance>() {
                    @Override
                    public void onResponse(Balance balance) {
                        showBalanceInfo(balance);
                    }
                });
        ApiUtil.apiService().info(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamInfo>() {
                    @Override
                    public void onResponse(TeamInfo response) {
                        showInfo(response);
                    }
                });
    }

    private void showInfo(TeamInfo info) {
        mTeamInfo = info;
        ViewUtil.setText(bindRoot.saplingActivity, String.valueOf(info.profile.minerActivityness));
        ViewUtil.setText(bindRoot.shareActivity, String.valueOf(info.profile.promotionActivityness));
        ViewUtil.setText(bindRoot.contribution, getString(R.string.contribution_value, String.valueOf(info.profile.contribution)));
    }

    private void showUserInfo() {
        TeamDetail userData = UserDataUtil.instance().getTeamDetail();
        if (userData == null)
            return;
        ViewUtil.setText(bindRoot.name, String.valueOf(userData.name));
        ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, userData.avatar);
    }

    private void showBalanceInfo(Balance balance) {
        mBalance = balance;
        ViewUtil.setText(bindRoot.scoreFruit, NumberUtils.instance().parseNumber(balance.diamond.amount));
        ViewUtil.setText(bindRoot.scoreBuild, NumberUtils.instance().parseNumber(balance.money.amount));
        ViewUtil.setText(bindRoot.level, getResources().getStringArray(R.array.person_level)[balance.level]);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icHead:
                ViewUtil.gotoActivity(this, UserInfoActivity.class);
                break;
            case R.id.wechat:
                ViewUtil.gotoActivity(this, WechatNoSetActivity.class);
                break;
            case R.id.myPartner:
                ViewUtil.gotoActivity(this, MyTeamActivity.class);
                break;
            case R.id.invitationCode:
                ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
            case R.id.fruitBill:
                if (mBalance != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("balance", mBalance);
                    ViewUtil.gotoActivity(this, FruitBillActivity.class, data);
                }
                break;
            case R.id.activitySapling:
            case R.id.activityShare:
                if (mTeamInfo != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("teamInfo", mTeamInfo);
                    ViewUtil.gotoActivity(this, ActiveBillActivity.class, data);
                }
                break;
            case R.id.buildScore:
                ViewUtil.gotoActivity(this, BuildScoreActivity.class);
                break;
            case R.id.exchangeScore:
                if (mBalance != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("balance", mBalance);
                    ViewUtil.gotoActivity(this, ExchangeBuildScoreActivity.class, data);
                }
                break;
        }
    }

}