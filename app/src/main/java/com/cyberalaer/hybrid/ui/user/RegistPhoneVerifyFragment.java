package com.cyberalaer.hybrid.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Region;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.NeteaseCaptcha;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.StringUtil;
import com.cyberalaer.hybrid.util.VerifyCodeCounter;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;
import likly.dollar.$;

@MvpBinder(
)
public class RegistPhoneVerifyFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> {

    Region region;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_phone_verify;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleText(R.string.apply);

        final TextWatcher watcher = new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        };

        bindRoot.etPhone.addTextChangedListener(watcher);
        bindRoot.etCode.addTextChangedListener(watcher);
    }

    private void onInputChanged() {
        final String phone = ViewUtil.getText(bindRoot.etPhone);
        final String code = ViewUtil.getText(bindRoot.etCode);
        boolean hasInputPhone = !TextUtils.isEmpty(phone);
        boolean hasInput = !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(code);
        bindRoot.btnSend.setEnabled(hasInputPhone);
        bindRoot.next.setEnabled(hasInput);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                verifyCode();
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
            case R.id.region:
                RegionActivity.startForResult(this);
                break;
            case R.id.agreement:
                WebPageActivity.start(getActivity(), AppConfig.USER_AGREEMENT, R.string.user_agreement);
                break;
        }
    }

    private void verifyCode() {
        if (!StringUtil.phoneIsValid(ViewUtil.getText(bindRoot.etPhone))) {
            $.toast().text(R.string.pls_input_valid_phone).show();
            return;
        }
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                getVerifyCode(validate);
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void getVerifyCode(String validate) {
        final String phone = ViewUtil.getText(bindRoot.etPhone);
        ApiUtil.apiService().getVCode(AppConfig.DIALLING_CODE, phone, AppConfig.VERIFY_ID, validate, "0",
                new Callback<String>() {
                    @Override
                    public void onResponse(String response) {
                        bindRoot.next.setEnabled(true);
                        $.toast().text(R.string.verify_code_send_ok).show();
                        VerifyCodeCounter.getInstance().startCounter(bindRoot.btnSend);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        VerifyCodeCounter.getInstance().destory();
    }

}