package llc.metaversenetwork.app.ui.wallet;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.CurrencyRecord;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import likly.view.repeat.OnHolderClickListener;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityWalletDetailBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 我的钱包
 */
public class CurrencyDetailActivity extends BaseTitleActivity<ActivityWalletDetailBinding> implements OnHolderClickListener<WalletAdapter> {
    // USDT:4
    // MNC:173
    AssetsTotalInfo.Assets mAssets;

    @Override
    protected int titleResId() {
        return R.string.my_wallet;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wallet_detail;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        getData();
    }

    private void getData() {
        mAssets = (AssetsTotalInfo.Assets) getIntent().getSerializableExtra("asset");
        setTitleText(getString(R.string.what_assets_detail, mAssets.currencyNameEn));
        bindRoot.setData(mAssets);
        bindRoot.setEnable(mAssets.currencyId == 4);
        bindRoot.setNumber(NumberUtils.instance());

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().queryCurrencyRecords(userData.uuid, String.valueOf(userData.uid), userData.token, String.valueOf(mAssets.currencyId),
                1, 100,
                new Callback<List<CurrencyRecord>>() {
                    @Override
                    public void onResponse(List<CurrencyRecord> records) {
                        showRecordList(records);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        showRecordList(null);
                    }
                });
    }

    private void showRecordList(List<CurrencyRecord> records) {
        if (CollectionUtils.isEmpty(records))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(records);
    }

    @Override
    public void onHolderClick(WalletAdapter walletAdapter) {
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.recharge:
                ViewUtil.gotoActivity(this, RechargeActivity.class);
                break;
            case R.id.withdrawal:
                ViewUtil.gotoActivity(this, WithdrawalActivity.class);
                break;
        }
    }

}