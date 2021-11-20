package llc.metaversenetwork.app.ui.wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.CoinAddress;
import com.alaer.lib.api.bean.CoinAuthConfig;
import com.alaer.lib.api.bean.CoinContract;
import com.alaer.lib.api.bean.TakeCoinInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import likly.view.repeat.OnHolderClickListener;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityRechargeBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.QRCodeEncoder;
import llc.metaversenetwork.app.util.ToastUtil;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 币种充值
 */
public class RechargeActivity extends BaseTitleActivity<ActivityRechargeBinding> implements OnHolderClickListener<WalletAdapter> {
    // USDT:4
    // MNC:173
    AssetsTotalInfo.Assets mAssets;
    int mCurrencyId = 4;

    public static List<CoinContract> mCoinContracts;
    UserData userData;
    CoinContract mCoinContract;
    String mCoinAddress;
    CoinAddress mCoinAddressInfo;
    TakeCoinInfo mTakeCoinInfo;
    CoinAuthConfig mCoinAuthConfig;

    @Override
    protected int titleResId() {
        return R.string.recharge;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        setRightTitleText(R.string.record);
        getData();
    }

    private void getData() {
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
        // 查询钱包地址
        queryCoinAddress();
    }

    private void queryCoinAddress() {
        if (mCoinContract == null)
            return;
        ApiUtil.apiService().coinAddress(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                "1", "4", String.valueOf(mCoinContract.contractId),
                new Callback<CoinAddress>() {
                    @Override
                    public void onResponse(CoinAddress coinAddress) {
                        showCoinAddress(coinAddress);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void showTakeCoinInfo() {
        if (mTakeCoinInfo == null || mCoinAuthConfig == null)
            return;

        String result = getString(R.string.recharge_desc, "USDT", mCoinContract.contract,
                mCoinAddressInfo.confirmNum, mCoinAddressInfo.confirmNum,
                String.valueOf(mCoinAuthConfig.amountLowLimit), "USDT", mCoinContract.contract);
        int tagLength = String.valueOf("USDT" + mCoinContract.contract).length() + 2;
        SpannableStringBuilder spannableString = new SpannableStringBuilder(result);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#357EEE")),
                result.indexOf("USDT"), result.indexOf("USDT") + tagLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF7171")),
                result.indexOf("3.") + 2, result.lastIndexOf("USDT") + tagLength,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        bindRoot.rechargeDesc.setText(spannableString);
    }

    private void showCoinAddress(CoinAddress coinAddress) {
        mCoinAddressInfo = coinAddress;
        mCoinAddress = coinAddress.address;
        Bitmap qrCode = QRCodeEncoder.createQRCode(coinAddress.address, 130);
        bindRoot.qrAddress.setImageBitmap(qrCode);
        bindRoot.coinAddress.setText(coinAddress.address);

        ApiUtil.apiService().selectTakeCoin(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                "4", String.valueOf(mCoinContract.contractId),
                new Callback<TakeCoinInfo>() {
                    @Override
                    public void onResponse(TakeCoinInfo takeCoinInfo) {
                        mTakeCoinInfo = takeCoinInfo;
                        queryCoinAuthConfig();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        super.onError(code, msg);
                    }
                });
    }

    private void queryCoinAuthConfig() {
        ApiUtil.apiService().coinAuthConfig(userData.uuid, String.valueOf(userData.uid), userData.token, "4","3",
                new Callback<CoinAuthConfig>() {
                    @Override
                    public void onResponse(CoinAuthConfig coinAuthConfig) {
                        mCoinAuthConfig = coinAuthConfig;
                        showTakeCoinInfo();
                    }
                });
    }

    @Override
    public void onHolderClick(WalletAdapter walletAdapter) {
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
            case R.id.copyAddress:
                ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mCoinAddress);
                ClipData clipData = ClipData.newPlainText(null, mCoinAddress);
                cmb.setPrimaryClip(clipData);
                ToastUtil.text(R.string.wallet_address_copy_success).show();
                break;
        }
    }

    @Override
    protected void onRightTitleClick() {
        ViewUtil.gotoActivity(this, RechargeRecordActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCoinContracts = null;
        mCoinContract = null;
    }
}