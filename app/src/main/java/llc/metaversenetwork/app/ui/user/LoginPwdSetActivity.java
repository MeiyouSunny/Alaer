package llc.metaversenetwork.app.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivitySecondPwdSetBinding;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.VerifyCodeCounter;
import llc.metaversenetwork.app.util.ViewUtil;

import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP1;
import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP2;

/**
 * 设置登录密码
 */
public class LoginPwdSetActivity extends BaseTitleActivity<ActivitySecondPwdSetBinding> {

    UserData userData;
    boolean haveSendCode;

    @Override
    protected int titleResId() {
        return R.string.login_pwd;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login_pwd_set;
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
        final boolean hasInput = !TextUtils.isEmpty(pwd)
                && !TextUtils.isEmpty(pwdConfirm)
                && !TextUtils.isEmpty(verifyCode);
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
                verifyCode(STEP1);
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
        if (!StringUtil.pwdIsValid(ViewUtil.getText(bindRoot.etPwd))) {
            $.toast().text(R.string.pwd_invalid).show();
            return;
        }
        if (!haveSendCode) {
            $.toast().text(R.string.pls_get_verify_code_first).show();
            return;
        }
        verifyCode(STEP2);
    }

    private void sendPhoneCode(String validate) {
        ApiUtil.apiService().getPhoneCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, 2,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        haveSendCode = true;
                        $.toast().text(R.string.verify_code_send_ok).show();
                        VerifyCodeCounter.getInstance().startCounter(bindRoot.send);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void verifyCode(@NeteaseCaptcha.STEP int step) {
        NeteaseCaptcha.start(getContext(), step, new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                if (step == STEP1) {
                    sendPhoneCode(validate);
                } else if (step == STEP2) {
                    resetPwd(validate);
                }
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void resetPwd(String validate) {
        ApiUtil.apiService().resetPwd(userData.phone, ViewUtil.getText(bindRoot.etVerifyCode), ViewUtil.getText(bindRoot.etPwd),
                validate, AppConfig.VERIFY_ID, AppConfig.DIALLING_CODE,
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        VerifyCodeCounter.getInstance().destory();
    }
}