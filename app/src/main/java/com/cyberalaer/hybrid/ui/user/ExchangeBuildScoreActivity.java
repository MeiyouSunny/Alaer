package com.cyberalaer.hybrid.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityExchangeScoreBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogInputSecondPwd;
import com.cyberalaer.hybrid.util.NumberUtils;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.StringUtil;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaConfiguration;
import com.netease.nis.captcha.CaptchaListener;

import org.json.JSONException;
import org.json.JSONObject;

import likly.dialogger.Dialogger;
import likly.dollar.$;

/**
 * 兑换建设积分
 */
public class ExchangeBuildScoreActivity extends BaseTitleActivity<ActivityExchangeScoreBinding> {

    UserData userData;
    Balance mBalance;
    String mTradePhraseCode;

    @Override
    protected int titleResId() {
        return R.string.exchangge_score;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_exchange_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        mBalance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(mBalance);

        bindRoot.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        });

        userData = UserDataUtil.instance().getUserData();
    }

    private void onInputChanged() {
        final String input = ViewUtil.getText(bindRoot.etAmount);
        boolean noInput = TextUtils.isEmpty(input);
        bindRoot.layoutInfo.setVisibility(noInput ? View.GONE : View.VISIBLE);
        if (noInput) {
            return;
        }

        bindRoot.tvFruitCount.setText(getString(R.string.fruit_number_is, input));

        float inputValue = 0F;
        if (input.contains("."))
            inputValue = Float.parseFloat(input);
        else
            inputValue = (float) Integer.parseInt(input);

        float amount = 100 * inputValue / (100 + mBalance.feeRate);
        float rateAmount = inputValue - amount;

        bindRoot.amount.setText(getString(R.string.score_number_is_about, NumberUtils.instance().parseNumber(amount)));
        bindRoot.rateAmount.setText(getString(R.string.fruit_number_is_about, NumberUtils.instance().parseNumber(rateAmount)));
        System.out.println("");

    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.exchangeAll:
                bindRoot.etAmount.setText(NumberUtils.instance().parseNumber(mBalance.diamond.amount));
                break;
            case R.id.confirm:
                showSecondPwdDialog();
                break;
        }
    }

    private void showSecondPwdDialog() {
        DialogInputSecondPwd dialog = new DialogInputSecondPwd();
        dialog.setListener(pwd -> confirmTransactionCode(pwd));
        Dialogger.newDialog(getContext()).holder(dialog).gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void confirmTransactionCode(String pwd) {
        if (userData == null)
            return;

        final String pwdMD5 = StringUtil.toMD5(pwd + AppConfig.MD5_KEY_TEMP_SECOND + userData.uid);
        ApiUtil.apiService().confirmTransactionCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, pwdMD5,
                new Callback<String>() {
                    @Override
                    public void onResponse(String json) {
                        if (!TextUtils.isEmpty(json)) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(json);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (jsonObject.has("code")) {
                                mTradePhraseCode = jsonObject.optString("code");
                                verifyCode();
                            }
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
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
                            exchangeFruit(validate, mTradePhraseCode);
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

    private void exchangeFruit(String validate, String tradePhraseCode) {
        ApiUtil.apiService().exchangeFruit(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                validate, AppConfig.VERIFY_ID, tradePhraseCode,
                new Callback<String>() {
                    @Override
                    public void onResponse(String tradePhraseCode) {
                        $.toast().text("兑换成功!").show();
                        finish();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

}