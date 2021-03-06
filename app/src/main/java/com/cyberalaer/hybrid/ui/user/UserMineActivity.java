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
import com.alaer.lib.api.bean.UserLevelList;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityUserMineBinding;
import com.cyberalaer.hybrid.ui.setting.AboutActivity;
import com.cyberalaer.hybrid.ui.setting.SecurityCenterActivity;
import com.cyberalaer.hybrid.ui.setting.SettingActivity;
import com.cyberalaer.hybrid.ui.share.ShareActivity;
import com.cyberalaer.hybrid.util.CollectionUtils;
import com.cyberalaer.hybrid.util.NumberUtils;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.UUID;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import likly.dollar.$;

/**
 * 个人中心
 */
public class UserMineActivity extends BaseTitleActivity<ActivityUserMineBinding> {

    UserData userData;
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
        setTitleRightIcon(R.drawable.ic_setting);
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    private void initData() {
        userData = UserDataUtil.instance().getUserData();
        if (userData == null) {
            bindRoot.layoutToLogin.setVisibility(View.VISIBLE);
            return;
        }

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

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showInfo(TeamInfo info) {
        mTeamInfo = info;
        ViewUtil.setText(bindRoot.saplingActivity, String.valueOf(NumberUtils.instance().parseNumber(info.profile.minerActivityness)));
        ViewUtil.setText(bindRoot.shareActivity, String.valueOf(NumberUtils.instance().parseNumber(info.profile.promotionActivityness)));
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
        queryLevels(mBalance.level);
    }

    @Override
    protected void onRightClick() {
        ViewUtil.gotoActivity(getContext(), SettingActivity.class);
    }

    private void queryLevels(int level) {
        ApiUtil.apiService().userLevels(userData.uuid, String.valueOf(userData.uid), userData.token,
                AppConfig.DIAMOND_CURRENCY,
                new Callback<UserLevelList>() {
                    @Override
                    public void onResponse(UserLevelList levels) {
                        if (levels != null && !CollectionUtils.isEmpty(levels.list) && level < levels.list.size()) {
                            bindRoot.userLevel.setVisibility(View.VISIBLE);
                            bindRoot.levelName.setText(levels.list.get(level).name);
                            final int[] imgs = new int[]{R.drawable.ic_user_level0, R.drawable.ic_user_level1, R.drawable.ic_user_level2,
                                    R.drawable.ic_user_level3, R.drawable.ic_user_level4, R.drawable.ic_user_level5, R.drawable.ic_user_level6};
                            bindRoot.icLevel.setBackgroundResource(imgs[level]);
                        }
                    }
                });
    }

    @Override
    public void click(View view) {
        if (view.getId() != R.id.customeService && view.getId() != R.id.about) {
            if (!judgeLogined())
                return;
        }

        switch (view.getId()) {
            case R.id.icHead:
                ViewUtil.gotoActivity(this, UserInfoActivity.class);
                break;
            case R.id.wechat:
                SetProfileActivity.start(this, SetProfileActivity.WECHAT);
                break;
            case R.id.invitationCode:
                if (UserDataUtil.instance().isFrom3DAccount())
                    $.toast().text(R.string.function_not_open).show();
                else
                    ViewUtil.gotoActivity(this, ShareActivity.class);
                break;
            case R.id.myPartner:
                ViewUtil.gotoActivity(this, MyTeamActivity.class);
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
                if (mBalance != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("balance", mBalance);
                    ViewUtil.gotoActivity(this, BuildScoreActivity.class, data);
                }
                break;
            case R.id.exchangeScore:
                if (mBalance != null) {
                    Bundle data = new Bundle();
                    data.putSerializable("balance", mBalance);
                    ViewUtil.gotoActivity(this, ExchangeBuildScoreActivity.class, data);
                }
                break;
            case R.id.about:
                ViewUtil.gotoActivity(this, AboutActivity.class);
                break;
            case R.id.securityCenter:
                ViewUtil.gotoActivity(this, SecurityCenterActivity.class);
                break;
            case R.id.contribution:
                if (mTeamInfo != null) {
                    Bundle data = new Bundle();
                    data.putInt("contribution", mTeamInfo.profile.contribution);
                    ViewUtil.gotoActivity(this, ContributionActivity.class, data);
                }
                break;
            case R.id.userLevel:
                if (mTeamInfo != null && mBalance != null) {
                    Bundle data = new Bundle();
                    data.putInt("level", mBalance.level);
                    data.putInt("contribution", mTeamInfo.profile.contribution);
                    ViewUtil.gotoActivity(this, UserLevelActivity.class, data);
                }
                break;
            case R.id.customeService:
                gotoCustomerService();
                break;
            case R.id.layoutToLogin:
                ViewUtil.gotoActivity(this, LoginActivity.class);
                break;
        }
    }

    private void gotoCustomerService() {
        UdeskSDKManager.getInstance().initApiKey(getApplicationContext(), AppConfig.UDESK_APP_DOMAIN,
                AppConfig.UDESK_APP_SECRETKEY, AppConfig.UDESK_APP_ID);
        final String sdkToken = UUID.randomUUID().toString();
        UdeskSDKManager.getInstance().entryChat(getApplicationContext(), UdeskConfig.createDefualt(), sdkToken);
    }

}