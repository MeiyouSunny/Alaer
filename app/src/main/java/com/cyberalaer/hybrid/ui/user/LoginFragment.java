package com.cyberalaer.hybrid.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Region;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;
import com.cyberalaer.hybrid.ui.home.HomeActivity;
import com.cyberalaer.hybrid.util.NeteaseCaptcha;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.StringUtil;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import likly.dollar.$;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> {

    private String mPhone, mPwd;
    Region region;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleText(R.string.enter);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.toRegist:
                navigate(R.id.action_to_regisPhoneVerify);
                break;
            case R.id.forgetPwd:
                navigate(R.id.action_to_restPwdFragment);
                break;
            case R.id.btnLogin:
                verifyCode();
                break;
            case R.id.region:
                RegionActivity.startForResult(this);
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        bindRoot.etPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChange();
            }
        });
        bindRoot.etPwd.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChange();
            }
        });

        judgeLoginedOrNot();
    }

    private void judgeLoginedOrNot() {
        UserData userData = UserDataUtil.instance().getSavedUserData();
        if (userData != null) {
            UserDataUtil.instance().getSavedTeamDetail();
            ViewUtil.gotoActivity(getContext(), HomeActivity.class);
            getActivity().finish();
        }
    }

    private void onInputChange() {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);
        boolean hasInput = !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPwd);
        bindRoot.btnLogin.setEnabled(hasInput);
    }

    private void verifyCode() {
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                login(validate);
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void login(String validate) {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);
        ApiUtil.apiService().login(mPhone, StringUtil.toMD5(mPwd + AppConfig.MD5_KEY_TEMP), validate,
                AppConfig.VERIFY_ID, "2", AppConfig.DIALLING_CODE,
                new Callback<UserData>() {
                    @Override
                    public void onResponse(UserData userData) {
                        UserDataUtil.instance().saveUserDataInfo(userData);

                        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                                new Callback<TeamDetail>() {
                                    @Override
                                    public void onResponse(TeamDetail teamDetail) {
                                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                                        ViewUtil.gotoActivity(getContext(), HomeActivity.class);
                                        getActivity().finish();
                                    }
                                });
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegionActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            region = (Region) data.getSerializableExtra("region");
            bindRoot.region.setText("+" + region.code);
        }
    }

}