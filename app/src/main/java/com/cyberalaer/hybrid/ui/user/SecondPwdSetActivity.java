package com.cyberalaer.hybrid.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivitySecondPwdSetBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import likly.dollar.$;

/**
 * 设置二级密码
 */
public class SecondPwdSetActivity extends BaseTitleActivity<ActivitySecondPwdSetBinding> {

    UserData userData;
    boolean haveSendCode;

    @Override
    protected int titleResId() {
        return R.string.second_pwd;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_second_pwd_set;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        TextWatcher textWatcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        };
        bindRoot.etPwd.addTextChangedListener(textWatcher);
        bindRoot.etPwdConfirm.addTextChangedListener(textWatcher);
        bindRoot.etVerifyCode.addTextChangedListener(textWatcher);

        userData = UserDataUtil.instance().getUserData();
    }

    private void onInputChanged() {
        String verifyCode = ViewUtil.getText(bindRoot.etVerifyCode);
        String pwd = ViewUtil.getText(bindRoot.etPwd);
        String pwdConfirm = ViewUtil.getText(bindRoot.etPwdConfirm);
        final boolean hasInput = !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdConfirm) && !TextUtils.isEmpty(verifyCode)
                && pwd.length() >= 8 && pwdConfirm.length() >= 8;
        bindRoot.submit.setEnabled(hasInput);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.icClear:
                bindRoot.etPwd.setText("");
                break;
            case R.id.icClear2:
                bindRoot.etPwdConfirm.setText("");
                break;
            case R.id.send:
                verifyCode(1);
                break;
            case R.id.submit:
                submit();
                break;
        }
    }

    private void submit() {
        if (!TextUtils.equals(ViewUtil.getText(bindRoot.etPwd), ViewUtil.getText(bindRoot.etPwdConfirm))) {
            $.toast().text(R.string.pwd_not_same).show();
            return;
        }
        if (!haveSendCode) {
            $.toast().text(R.string.pls_get_verify_code_first).show();
            return;
        }
        verifyCode(2);
    }

    private void sendPhoneCode(String validate) {
        ApiUtil.apiService().getPhoneCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, 2,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        haveSendCode = true;
                        $.toast().text(R.string.verify_code_send_ok).show();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    // type 1:获取验证码; 2:重置密码
    private void verifyCode(int type) {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        if (!TextUtils.isEmpty(validate)) {
                            if (type == 1) {
                                sendPhoneCode(validate);
                            } else if (type == 2) {
                                resetPwd(validate);
                            }
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

    private void resetPwd(String validate) {
        ApiUtil.apiService().resetSecondPwd(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, ViewUtil.getText(bindRoot.etPwd), ViewUtil.getText(bindRoot.etVerifyCode),
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.reset_pwd_success).show();
                        finish();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

}