package llc.metaversenetwork.app.ui.wallet;

import android.view.View;

import com.alaer.lib.api.bean.AssetsTotalInfo;

import likly.view.repeat.OnHolderClickListener;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityRechargeBinding;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 币种充值
 */
public class RechargeActivity extends BaseTitleActivity<ActivityRechargeBinding> implements OnHolderClickListener<WalletAdapter> {
    // USDT:4
    // MNC:173
    AssetsTotalInfo.Assets mAssets;
    int mCurrencyId = 4;

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
//        getData();
    }

//    private void getData() {
//        mAssets = (AssetsTotalInfo.Assets) getIntent().getSerializableExtra("asset");
//        bindRoot.setData(mAssets);
//        bindRoot.setCurrencyId(mCurrencyId);
//
//        UserData userData = UserDataUtil.instance().getUserData();
//        if (userData == null)
//            return;
//        ApiUtil.apiService().queryCurrencyRechargeRecords(userData.uuid, String.valueOf(userData.uid), userData.token, String.valueOf(mAssets.currencyId),
//                1, 100, 173, "", "", "",
//                new Callback<String>() {
//                    @Override
//                    public void onResponse(String records) {
//                        showRecordList(null);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        showRecordList(null);
//                    }
//                });
//    }

//    private void showRecordList(List<CurrencyRecord> records) {
//        bindRoot.repeatView.onClick(this);
//
//        if (CollectionUtils.isEmpty(records))
//            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
//        else
//            bindRoot.repeatView.viewManager().bind(records);
//    }

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
        }
    }

    @Override
    protected void onRightTitleClick() {
        ViewUtil.gotoActivity(this, CurrencyRechargeRecordActivity.class);
    }
}