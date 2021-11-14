package llc.metaversenetwork.app.ui.wallet;

import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.CoinContract;
import com.alaer.lib.api.bean.TakeCoinInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.api.bean.WithdrawConfirmInfo;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityWithdrawalBinding;
import llc.metaversenetwork.app.ui.dialog.DialogInputSecondPwd;
import llc.metaversenetwork.app.ui.dialog.DialogWithdrawInfoConfirm;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NeteaseCaptcha;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.StringUtil;
import llc.metaversenetwork.app.util.VerifyCodeCounter;
import llc.metaversenetwork.app.util.ViewUtil;

import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP1;
import static llc.metaversenetwork.app.util.NeteaseCaptcha.STEP2;

/**
 * 币种提现
 */
public class WithdrawalActivity extends BaseTitleActivity<ActivityWithdrawalBinding> {
    public static List<CoinContract> mCoinContracts;
    AssetsTotalInfo.Assets mAssets;
    int mCurrencyId = 4;
    // USDT:4
    // MNC:173
    UserData userData;
    CoinContract mCoinContract;
    TakeCoinInfo mTakeCoinInfo;
    String mTradePhraseCode;

    @Override
    protected int titleResId() {
        return R.string.withdrawal;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        setRightTitleText(R.string.record);
        getData();
    }

    @Override
    protected void onRightTitleClick() {
        ViewUtil.gotoActivity(this, WithdrawalRecordActivity.class);
    }

    private void getData() {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.etAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        });
        bindRoot.etVerifyCode.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                onInputChanged();
            }
        });

        userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().coinContract(userData.uuid, String.valueOf(userData.uid), userData.token, "4",
                new Callback<List<CoinContract>>() {
                    @Override
                    public void onResponse(List<CoinContract> contracts) {
                        mCoinContracts = contracts;
                        showCoinContract();
                    }
                });
    }

    private void onInputChanged() {
        final String input = ViewUtil.getText(bindRoot.etAmount);
        final String verifyCode = ViewUtil.getText(bindRoot.etVerifyCode);
        boolean hasInput = !TextUtils.isEmpty(input) && !TextUtils.isEmpty(verifyCode);
        bindRoot.submit.setEnabled(hasInput);
    }

    private void showCoinContract() {
        if (CollectionUtils.isEmpty(mCoinContracts))
            return;
        for (int i = 0; i < mCoinContracts.size(); i++) {
            TextView view = (TextView) getLayoutInflater().inflate(R.layout.item_coin_contract, null);
            view.setText(mCoinContracts.get(i).contract);

            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 35;
            bindRoot.layoutContract.addView(view, layoutParams);

            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    coinContractSelect(finalI);
                }
            });
        }

        coinContractSelect(0);
    }

    private void coinContractSelect(int index) {
        mCoinContract = mCoinContracts.get(index);
        int size = bindRoot.layoutContract.getChildCount();
        for (int i = 0; i < size; i++) {
            if (index == i)
                bindRoot.layoutContract.getChildAt(i).setBackgroundResource(R.drawable.btn_blue);
            else
                bindRoot.layoutContract.getChildAt(i).setBackgroundResource(R.drawable.btn_gray_trans);
        }

        queryWithdrawalInfo();
    }

    private void queryWithdrawalInfo() {
        ApiUtil.apiService().selectTakeCoin(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                "4", String.valueOf(mCoinContract.contractId),
                new Callback<TakeCoinInfo>() {
                    @Override
                    public void onResponse(TakeCoinInfo takeCoinInfo) {
                        showTakeCoinInfo(takeCoinInfo);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showTakeCoinInfo(TakeCoinInfo takeCoinInfo) {
        mTakeCoinInfo = takeCoinInfo;
        bindRoot.setTakeCoinInfo(mTakeCoinInfo);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.typeUSDT:
                if (mCurrencyId != 4) {
                    mCurrencyId = 4;
                    bindRoot.setCurrencyId(mCurrencyId);
                }
                break;
            case R.id.typeMNC:
                if (mCurrencyId != 13) {
                    mCurrencyId = 13;
                    bindRoot.setCurrencyId(mCurrencyId);
                }
                break;
            case R.id.sendVerifyCode:
                neteaseCaptcha(NeteaseCaptcha.STEP1);
                break;
            case R.id.withdrawalAll:
                if (mTakeCoinInfo != null) {
                    bindRoot.etAmount.setText(NumberUtils.instance().parseNumber(mTakeCoinInfo.resp.cashAmount));
                    bindRoot.etAmount.setSelection(ViewUtil.getText(bindRoot.etAmount).length());
                }
                break;
            case R.id.submit:
                showInfoConfirmDialog();
                break;
        }
    }

    private void showInfoConfirmDialog() {
        WithdrawConfirmInfo confirmInfo = new WithdrawConfirmInfo();
        String coinAddress = ViewUtil.getText(bindRoot.coinAddress);
        String amount = ViewUtil.getText(bindRoot.etAmount);
        confirmInfo.coinAddress = coinAddress;
        confirmInfo.amount = amount;
        confirmInfo.contract = mCoinContract.contract;
        confirmInfo.currency = "USDT";
        confirmInfo.fee = mTakeCoinInfo.resp.fee;
        DialogWithdrawInfoConfirm dialogConfirm = new DialogWithdrawInfoConfirm(confirmInfo);
        dialogConfirm.setListener(new DialogWithdrawInfoConfirm.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                showSecondPwdDialog();
            }
        });
        Dialogger.newDialog(getContext()).holder(dialogConfirm)
                .gravity(Gravity.CENTER)
                .cancelable(false)
                .show();
    }

    private void neteaseCaptcha(int step) {
        NeteaseCaptcha.start(getContext(), step, new NeteaseCaptcha.OnCaptchaListener() {
            @Override
            public void onCaptchaSuccess(String validate) {
                if (step == STEP1) {
                    sendVerifyCode(validate);
                } else if (step == STEP2) {
                    withdrawal(validate);
                }
            }

            @Override
            public void onCaptchaError(String msg) {
                $.toast().text(msg).show();
            }
        });
    }

    private void sendVerifyCode(String validate) {
        ApiUtil.apiService().withdrawVcode(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                AppConfig.VERIFY_ID, validate, "2",
                new Callback<String>() {
                    @Override
                    public void onResponse(String result) {
                        $.toast().text(R.string.verify_code_send_ok).show();
                        VerifyCodeCounter.getInstance().startCounter(bindRoot.sendVerifyCode);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        $.toast().text(msg).show();
                    }
                });
    }

    private void showSecondPwdDialog() {
        DialogInputSecondPwd dialog = new DialogInputSecondPwd();
        dialog.setListener(pwd -> confirmTransactionCode(pwd));
        Dialogger.newDialog(getContext()).holder(dialog).gravity(Gravity.CENTER).cancelable(false).show();
    }

    private void confirmTransactionCode(String pwd) {
        if (!TextUtils.isEmpty(pwd)) {
            mTradePhraseCode = StringUtil.toMD5(pwd + AppConfig.MD5_KEY_TEMP_SECOND + userData.uid);
            neteaseCaptcha(NeteaseCaptcha.STEP2);
        }
    }

    private void withdrawal(String validate) {
        String verifyCode = ViewUtil.getText(bindRoot.etVerifyCode);
        String coinAddress = ViewUtil.getText(bindRoot.coinAddress);
        String amount = ViewUtil.getText(bindRoot.etAmount);

        ApiUtil.apiService().withdraw(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                "4", mTradePhraseCode, "",
                coinAddress, amount, AppConfig.VERIFY_ID,
                "4", validate, "",
                verifyCode, String.valueOf(mCoinContract.contractId),
                new Callback<String>() {
                    @Override
                    public void onResponse(String result) {
                        $.toast().text(R.string.withdraw_success).show();
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
        mCoinContracts = null;
        mCoinContract = null;
        VerifyCodeCounter.getInstance().destory();
    }

}