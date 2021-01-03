package com.cyberalaer.hybrid.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import likly.dollar.$;

@MvpBinder(
)
public class RegistPhoneVerifyFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_phone_verify;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopLeftIcon(R.drawable.ic_back_close);
        setTitleText(R.string.apply);

        bindRoot.etPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                bindRoot.btnSend.setEnabled(!TextUtils.isEmpty(ViewUtil.getText(bindRoot.etPhone)));
            }
        });
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                captcha();
                break;
            case R.id.toLogin:
                navigate(R.id.action_to_login);
                break;
            case R.id.next:
                Bundle bundle = new Bundle();
                bundle.putString("phone", ViewUtil.getText(bindRoot.etPhone));
                bundle.putString("verifyCode", ViewUtil.getText(bindRoot.etCode));
                navigate(R.id.action_to_registConfirmPwd, bundle);
                break;
        }
    }

    private void captcha() {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        if (!TextUtils.isEmpty(validate)) {
                            getVerifyCode(validate);
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
        ApiUtil.apiService().getVCode(AppConfig.DIALLING_CODE_DEFAULT, phone, AppConfig.VERIFY_ID, validate, "0",
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        bindRoot.next.setEnabled(true);
                        $.toast().text(R.string.verify_code_send_ok).show();
                    }
                });
    }

}