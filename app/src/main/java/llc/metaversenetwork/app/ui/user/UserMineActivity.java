package llc.metaversenetwork.app.ui.user;

import android.os.Bundle;
import android.view.Gravity;
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

import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityUserMineBinding;
import llc.metaversenetwork.app.ui.dialog.DialogNotAuth;
import llc.metaversenetwork.app.ui.setting.AboutActivity;
import llc.metaversenetwork.app.ui.setting.SecurityCenterActivity;
import llc.metaversenetwork.app.ui.setting.SettingActivity;
import llc.metaversenetwork.app.ui.share.ShareActivity;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.ViewUtil;

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
        ViewUtil.setText(bindRoot.contributionValue, String.valueOf(NumberUtils.instance().parseNumber(info.profile.contribution)));
        ViewUtil.setText(bindRoot.honorValue, String.valueOf(NumberUtils.instance().parseNumber(info.profile.reputation)));
    }

    private void showUserInfo() {
        TeamDetail userData = UserDataUtil.instance().getTeamDetail();
        if (userData == null)
            return;
        ViewUtil.setText(bindRoot.name, String.valueOf(userData.name));
        ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, userData.avatar);
    }

    public static int level;
    private void showBalanceInfo(Balance balance) {
        level = mBalance.level;
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
                            bindRoot.levelName.setText(levels.list.get(level).name);
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
                    $.toast().text(R.string.will_open_soon).show();
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
                // 是否实名认证
                if (!UserDataUtil.instance().isAuthed()) {
                    showNotAuthDialog();
                } else {
                    if (mBalance != null) {
                        Bundle data = new Bundle();
                        data.putSerializable("balance", mBalance);
                        ViewUtil.gotoActivity(this, ExchangeBuildScoreActivity.class, data);
                    }
                }
                break;
            case R.id.about:
                ViewUtil.gotoActivity(this, AboutActivity.class);
                break;
            case R.id.securityCenter:
                ViewUtil.gotoActivity(this, SecurityCenterActivity.class);
                break;
            case R.id.activityContribution:
                if (mTeamInfo != null) {
                    Bundle data = new Bundle();
                    data.putInt("contribution", mTeamInfo.profile.contribution);
                    ViewUtil.gotoActivity(this, ContributionActivity.class, data);
                }
                break;
            case R.id.activityHonorValue:
                if (mTeamInfo != null) {
                    Bundle data = new Bundle();
                    data.putFloat("honor", mTeamInfo.profile.reputation);
                    ViewUtil.gotoActivity(this, HonorRecordActivity.class, data);
                }
                break;
            case R.id.levelName:
                if (mTeamInfo != null && mBalance != null) {
                    Bundle data = new Bundle();
                    data.putInt("level", mBalance.level);
                    data.putInt("contribution", mTeamInfo.profile.contribution);
                    ViewUtil.gotoActivity(this, UserLevelActivity.class, data);
                }
                break;
            case R.id.customeService:
                ViewUtil.gotoCustomerService(this);
                break;
            case R.id.layoutToLogin:
                ViewUtil.gotoActivity(this, LoginActivity.class);
                break;
            case R.id.myWallet:
                $.toast().text(R.string.will_open_soon).show();
//                ViewUtil.gotoActivity(this, WalletActivity.class);
                break;
        }
    }

    // 未实名认证提示
    private void showNotAuthDialog() {
        DialogNotAuth dialogNotAuth = new DialogNotAuth();
        dialogNotAuth.setListener(new DialogNotAuth.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                ViewUtil.gotoAuthPage(getContext());
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogNotAuth)
                .gravity(Gravity.CENTER)
                .show();
    }

}