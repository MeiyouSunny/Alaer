package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.UserData;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityExchangeScoreBinding;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;

/**
 * 资产兑换
 */
public class ExchangeBuildScoreActivity extends BaseTitleActivity<ActivityExchangeScoreBinding> {

    UserData userData;
    Balance mBalance;
    String mTradePhraseCode;

    @Override
    protected int titleResId() {
        return R.string.assets_exchange;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_exchange_score;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setTitleRightIcon(R.drawable.ic_question);

//        mBalance = (Balance) getIntent().getSerializableExtra("balance");
//        bindRoot.setNumber(NumberUtils.instance());
//        bindRoot.setBalance(mBalance);
//
//        bindRoot.etAmount.addTextChangedListener(new SimpleTextWatcher() {
//            @Override
//            public void afterTextChanged(Editable editable) {
//                onInputChanged();
//            }
//        });
//
//        userData = UserDataUtil.instance().getUserData();
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, AppConfig.EXCHANGE_EXPLAIN, R.string.exchange_explain);
    }

//    private void onInputChanged() {
//        final String input = ViewUtil.getText(bindRoot.etAmount);
//        boolean noInput = TextUtils.isEmpty(input);
//        bindRoot.layoutInfo.setVisibility(noInput ? View.GONE : View.VISIBLE);
//        bindRoot.confirm.setEnabled(!noInput);
//        if (noInput) {
//            return;
//        }
//
//        bindRoot.tvFruitCount.setText(getString(R.string.fruit_number_is, input));
//
//        float inputValue = 0F;
//        if (input.contains("."))
//            inputValue = Float.parseFloat(input);
//        else
//            inputValue = (float) Integer.parseInt(input);
//
//        float amount = 100 * inputValue / (100 + mBalance.feeRate);
//        float rateAmount = inputValue - amount;
//
//        bindRoot.amount.setText(getString(R.string.score_number_is_about, NumberUtils.instance().parseNumber(amount)));
//        bindRoot.rateAmount.setText(getString(R.string.fruit_number_is_about, NumberUtils.instance().parseNumber(rateAmount)));
//        System.out.println("");
//
//    }
//
//    @Override
//    public void click(View view) {
//        switch (view.getId()) {
//            case R.id.exchangeAll:
//                bindRoot.etAmount.setText(NumberUtils.instance().parseNumber(mBalance.diamond.amount));
//                break;
//            case R.id.confirm:
//                showSecondPwdDialog();
//                break;
//        }
//    }
//
//    private void showSecondPwdDialog() {
//        DialogInputSecondPwd dialog = new DialogInputSecondPwd();
//        dialog.setListener(pwd -> confirmTransactionCode(pwd));
//        Dialogger.newDialog(getContext()).holder(dialog).gravity(Gravity.CENTER).cancelable(false).show();
//    }
//
//    private void confirmTransactionCode(String pwd) {
//        if (userData == null)
//            return;
//
//        final String pwdMD5 = StringUtil.toMD5(pwd + AppConfig.MD5_KEY_TEMP_SECOND + userData.uid);
//        ApiUtil.apiService().confirmTransactionCode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, pwdMD5,
//                new Callback<String>() {
//                    @Override
//                    public void onResponse(String json) {
//                        if (!TextUtils.isEmpty(json)) {
//                            JSONObject jsonObject = null;
//                            try {
//                                jsonObject = new JSONObject(json);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            if (jsonObject.has("code")) {
//                                mTradePhraseCode = jsonObject.optString("code");
//                                verifyCode();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        $.toast().text(msg).show();
//                    }
//                });
//    }
//
//    private void verifyCode() {
//        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
//            @Override
//            public void onCaptchaSuccess(String validate) {
//                exchangeFruit(validate, mTradePhraseCode);
//            }
//
//            @Override
//            public void onCaptchaError(String msg) {
//                $.toast().text(msg).show();
//            }
//        });
//    }
//
//    private void exchangeFruit(String validate, String tradePhraseCode) {
//        ApiUtil.apiService().exchangeFruit(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
//                validate, AppConfig.VERIFY_ID, tradePhraseCode, ViewUtil.getText(bindRoot.etAmount),
//                new Callback<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        $.toast().text(R.string.exchange_success).show();
//                        finish();
//                    }
//
//                    @Override
//                    public void onError(int code, String msg) {
//                        $.toast().text(msg).show();
//                    }
//                });
//    }

}