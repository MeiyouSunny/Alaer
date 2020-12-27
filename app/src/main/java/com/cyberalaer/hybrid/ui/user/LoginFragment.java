package com.cyberalaer.hybrid.ui.user;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alaer.lib.api.AesUtil;
import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;
import com.meiyou.mvp.MvpBinder;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> {

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

    private void verifyCode() {
        final CaptchaConfiguration configuration = new CaptchaConfiguration.Builder()
                .captchaId(AppConfig.VERIFY_ID)
                .listener(new CaptchaListener() {
                    @Override
                    public void onReady() {
                        Log.e("Captcha", "onReady");
                    }

                    @Override
                    public void onValidate(String result, String validate, String msg) {
                        Log.e("Captcha", "result:" + result + "\nvalidate:" + validate + "\nmsg:" + msg);
                        if (!TextUtils.isEmpty(validate)) {
                            login(validate);
                        }
                    }

                    @Override
                    public void onError(String s) {
                        Log.e("Captcha", "onError " + s);
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Captcha", "onCancel");
                    }
                })
                .build(getContext());
        final Captcha captcha = Captcha.getInstance().init(configuration);
        captcha.validate();

    }

    private void login(String validate) {
        ApiUtil.apiService().login("33330301", AesUtil.encrypt("asdfgh123"), validate, "2",
//        ApiUtil.apiService().login("33330301", "asdfgh123", validate, "2",
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                    }
                });
    }

}