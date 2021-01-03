package com.cyberalaer.hybrid.ui.user;

import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentResetPwdBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import likly.dollar.$;

@MvpBinder(
)
public class RestPwdFragment extends BaseBindFragment<FragmentResetPwdBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopLeftIcon(R.drawable.ic_back_black);
        setTitleText(R.string.reset_pwd);

        bindRoot.etPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindRoot.btnSend.setEnabled(!TextUtils.isEmpty(ViewUtil.getText(bindRoot.etPhone)));
            }
        });

        TextWatcher textWatcher = new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                onInputChanged();
            }
        };
        bindRoot.etCode.addTextChangedListener(textWatcher);
        bindRoot.etPwd.addTextChangedListener(textWatcher);
        bindRoot.etPwdConfirm.addTextChangedListener(textWatcher);
    }

    private void onInputChanged() {
        String verifyCode = ViewUtil.getText(bindRoot.etCode);
        String pwd = ViewUtil.getText(bindRoot.etPwd);
        String pwdConfirm = ViewUtil.getText(bindRoot.etPwdConfirm);
        final boolean hasInput = !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdConfirm) && !TextUtils.isEmpty(verifyCode)
                && pwd.length() >= 8 && pwdConfirm.length() >= 8;
        bindRoot.confirm.setEnabled(hasInput);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                captcha(1);
                break;
            case R.id.confirm:
                captcha(2);
                break;
        }
    }

    private void captcha(int type) {
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
                                getVerifyCode(validate);
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

    private void getVerifyCode(String validate) {
        final String phone = ViewUtil.getText(bindRoot.etPhone);
        ApiUtil.apiService().getVCode(AppConfig.DIALLING_CODE_DEFAULT, phone, AppConfig.VERIFY_ID, validate, "1",
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.verify_code_send_ok).show();
                    }
                });
    }

    private void resetPwd(String validate) {
        if (!TextUtils.equals(ViewUtil.getText(bindRoot.etPwd), ViewUtil.getText(bindRoot.etPwdConfirm))) {
            $.toast().text(R.string.pwd_not_same).show();
            return;
        }
        ApiUtil.apiService().resetPwd(ViewUtil.getText(bindRoot.etPhone), ViewUtil.getText(bindRoot.etCode), ViewUtil.getText(bindRoot.etPwd),
                validate, AppConfig.VERIFY_ID,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        $.toast().text(R.string.reset_pwd_success).show();
                        navigate(R.id.action_to_login);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });

    }

}