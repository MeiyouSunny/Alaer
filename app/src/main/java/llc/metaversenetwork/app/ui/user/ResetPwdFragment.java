package llc.metaversenetwork.app.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Region;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentResetPwdBinding;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.util.VerifyCodeCounter;
import llc.metaversenetwork.app.util.ViewUtil;

import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP1;
import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP2;

@MvpBinder(
)
public class ResetPwdFragment extends BaseBindFragment<FragmentResetPwdBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleText(R.string.reset_pwd);
        bindRoot.region.setText("+" + AppConfig.DIALLING_CODE);

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
                verifyCode(STEP1);
                break;
            case R.id.confirm:
                verifyCode(STEP2);
                break;
            case R.id.region:
                RegionActivity.startForResult(this);
                break;
        }
    }

    private void verifyCode(@NeteaseCaptcha.STEP int step) {
        if (step == STEP1) {
            if (!StringUtil.phoneIsValid(ViewUtil.getText(bindRoot.etPhone))) {
                ToastUtil.text(R.string.pls_input_valid_phone).show();
                return;
            }
        }

        if (step == STEP2) {
            if (!StringUtil.pwdIsValid(ViewUtil.getText(bindRoot.etPwd))) {
                ToastUtil.text(R.string.pwd_invalid).show();
                return;
            }
        }

        NeteaseCaptcha.start(getContext(), step, new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                if (step == STEP1) {
                    getVerifyCode(validate);
                } else if (step == STEP2) {
                    resetPwd(validate);
                }
            }

            @Override
            public void onCaptchaError(String msg) {
                ToastUtil.text(msg).show();
            }
        });
    }

    private void getVerifyCode(String validate) {
        final String phone = ViewUtil.getText(bindRoot.etPhone);
        ApiUtil.apiService().getVCode(AppConfig.DIALLING_CODE, phone, AppConfig.VERIFY_ID, validate, "1",
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.text(R.string.verify_code_send_ok).show();
                        VerifyCodeCounter.getInstance().startCounter(bindRoot.btnSend);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });
    }

    private void resetPwd(String validate) {
        if (!TextUtils.equals(ViewUtil.getText(bindRoot.etPwd), ViewUtil.getText(bindRoot.etPwdConfirm))) {
            ToastUtil.text(R.string.pwd_not_same).show();
            return;
        }
        ApiUtil.apiService().resetPwd(ViewUtil.getText(bindRoot.etPhone), ViewUtil.getText(bindRoot.etCode), ViewUtil.getText(bindRoot.etPwd),
                validate, AppConfig.VERIFY_ID, AppConfig.DIALLING_CODE,
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.text(R.string.reset_pwd_success).show();
                        navigate(R.id.action_to_login);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        ToastUtil.text(msg).show();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegionActivity.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            final Region region = (Region) data.getSerializableExtra("region");
            bindRoot.region.setText("+" + region.code);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VerifyCodeCounter.getInstance().destory();
    }

}