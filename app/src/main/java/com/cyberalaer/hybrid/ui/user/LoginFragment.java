package com.cyberalaer.hybrid.ui.user;

import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.StringUtil;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import likly.dollar.$;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> {

    private String mPhone, mPwd;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopLeftIcon(R.drawable.ic_back_black);
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
    }

    private void onInputChange() {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);
        boolean hasInput = !TextUtils.isEmpty(mPhone) && !TextUtils.isEmpty(mPwd);
        bindRoot.btnLogin.setEnabled(hasInput);
    }

    private void verifyCode() {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        if (!TextUtils.isEmpty(validate)) {
                            login(validate);
                        }
                    }

                    @Override
                    public void onError(String s) {
                    }

                    @Override
                    public void onCancel() {
                    }
                })
                .build(getContext());
        final Captcha captcha = Captcha.getInstance().init(configuration);
        captcha.validate();

    }

    private void login(String validate) {
        mPhone = ViewUtil.getText(bindRoot.etPhone);
        mPwd = ViewUtil.getText(bindRoot.etPwd);
        ApiUtil.apiService().login(mPhone, StringUtil.toMD5(mPwd + AppConfig.MD5_KEY_TEMP), validate, "2",
                new Callback<UserData>() {
                    @Override
                    public void onResponse(UserData userData) {
                        $.toast().text(userData.phone).show();
                        UserDataUtil.instance().setUserData(userData);

                        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, "174",
                                new Callback<TeamDetail>() {
                                    @Override
                                    public void onResponse(TeamDetail teamDetail) {
                                        UserDataUtil.instance().setTeamDetail(teamDetail);

                                        ApiUtil.apiService().getUserInfo(userData.uid, userData.uuid, userData.token,
                                                new Callback<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        super.onResponse(response);
                                                    }
                                                });
                                    }
                                });

                    }
                });
    }

}