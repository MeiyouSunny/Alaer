package llc.metaversenetwork.app.ui.user;

import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityExchangeScoreBinding;
import llc.metaversenetwork.app.ui.dialog.DialogInputSecondPwd;
import llc.metaversenetwork.app.ui.webpage.WebPageActivity;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 资产兑换
 */
public class ExchangeBuildScoreActivity extends BaseTitleActivity<ActivityExchangeScoreBinding> {
    // balance.diamond.amount : WMNC
    // balance.money.amount : MNC

    // 是否WMNC-->MNC
    boolean mIsWMNC = true;
    // 待兑换和获得币种余额
    float mAmountInput, mAmountGet;
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

        mBalance = (Balance) getIntent().getSerializableExtra("balance");
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBalance(mBalance);
        bindRoot.setIsWMNC(mIsWMNC);

        bindRoot.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        });

        userData = UserDataUtil.instance().getUserData();

        updateShow();

        String personalLevel = getString(R.string.person_level_is, mBalance.level);
        bindRoot.personLevel.setText(personalLevel);
        String serviceFee = getString(R.string.service_charge_is, mBalance.feeRate + "%");
        bindRoot.serviceFee.setText(serviceFee);

        bindRoot.spinnerInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIsWMNC = (position == 0);
                changeType();
                bindRoot.spinnerGet.setSelection(mIsWMNC ? 1 : 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bindRoot.spinnerGet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIsWMNC = (position == 1);
                changeType();
                bindRoot.spinnerInput.setSelection(mIsWMNC ? 0 : 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onRightClick() {
        WebPageActivity.start(this, getString(R.string.assets_exchange_desc_url), R.string.exchange_explain);
    }

    private void updateShow() {
        mAmountInput = mIsWMNC ? mBalance.diamond.amount : mBalance.money.amount;
        mAmountGet = mIsWMNC ? mBalance.money.amount : mBalance.diamond.amount;

        bindRoot.balance1.setText(getString(R.string.balance_is, NumberUtils.instance().parseNumber(mAmountInput)));
        bindRoot.balance2.setText(getString(R.string.balance_is, NumberUtils.instance().parseNumber(mAmountGet)));

        onInputChanged();
    }

    private void onInputChanged() {
        final String input = ViewUtil.getText(bindRoot.etAmount);
        boolean noInput = TextUtils.isEmpty(input);
        bindRoot.confirm.setEnabled(!noInput);
        if (noInput) {
            calculateAndShow(0F);
            return;
        }

        float inputValue = 0F;
        if (input.contains("."))
            inputValue = Float.parseFloat(input);
        else
            inputValue = (float) Integer.parseInt(input);

        calculateAndShow(inputValue);
    }

    private void calculateAndShow(float inputValue) {
        int feeRate = mIsWMNC ? mBalance.feeRate : 0;
        float amountGet = inputValue;
        if (mIsWMNC)
            amountGet = 100 * inputValue / (100 + feeRate);
        amountGet = NumberUtils.instance().parseFloat(amountGet);

        float feeAmount = NumberUtils.instance().parseFloat(inputValue - amountGet);
        String unitInput = mIsWMNC ? getString(R.string.sapling) : getString(R.string.mnc_unit);
        String unitGet = mIsWMNC ? getString(R.string.mnc_unit) : getString(R.string.sapling);

        bindRoot.amountGet.setText(NumberUtils.instance().parseNumber(amountGet));
        bindRoot.detailLabel22.setText(inputValue + " " + unitInput);
        bindRoot.detailLabel32.setText(feeAmount + " " + unitInput);
        bindRoot.detailLabel42.setText(amountGet + " " + unitGet);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.exchangeAll:
                bindRoot.etAmount.setText(NumberUtils.instance().parseNumber(mAmountInput));
                bindRoot.etAmount.setSelection(ViewUtil.getText(bindRoot.etAmount).length());
                break;
            case R.id.confirm:
                showSecondPwdDialog();
                break;
        }
    }

    // 跟换兑换模式
    private void changeType() {
        updateShow();
//        bindRoot.etAmount.setText("");
        bindRoot.setIsWMNC(mIsWMNC);
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
        NeteaseCaptcha.start(getContext(), new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                exchangeFruit(validate, mTradePhraseCode);
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void exchangeFruit(String validate, String tradePhraseCode) {
        if (mIsWMNC) {
            ApiUtil.apiService().exchangeFruit(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.WMNC_ID,
                    validate, AppConfig.VERIFY_ID, tradePhraseCode, ViewUtil.getText(bindRoot.etAmount),
                    new Callback<String>() {
                        @Override
                        public void onResponse(String response) {
                            $.toast().text(R.string.exchange_success).show();
                            finish();
                        }

                        @Override
                        public void onError(int code, String msg) {
                            $.toast().text(msg).show();
                        }
                    });
        } else {
            ApiUtil.apiService().exchangeAssets(userData.uuid, String.valueOf(userData.uid), userData.token,
                    validate, AppConfig.VERIFY_ID, tradePhraseCode, ViewUtil.getText(bindRoot.etAmount), AppConfig.WMNC_ID, AppConfig.MNC_ID,
                    new Callback<String>() {
                        @Override
                        public void onResponse(String response) {
                            $.toast().text(R.string.exchange_success).show();
                            finish();
                        }

                        @Override
                        public void onError(int code, String msg) {
                            $.toast().text(msg).show();
                        }
                    });
        }
    }

}