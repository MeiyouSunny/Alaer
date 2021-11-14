package llc.metaversenetwork.app.ui.wallet;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.RechargeData;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityCurrencyRechargeRecordBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 币种充值记录
 */
public class RechargeRecordActivity extends BaseTitleActivity<ActivityCurrencyRechargeRecordBinding> {
    // USDT:4
    // MNC:173
    AssetsTotalInfo.Assets mAssets;
    int mCurrencyId = 4;

    @Override
    protected int titleResId() {
        return R.string.recharge_record;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_currency_recharge_record;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        setRightTitleText(R.string.call_customer_service);
        getData();
    }

    private void getData() {
        mAssets = (AssetsTotalInfo.Assets) getIntent().getSerializableExtra("asset");
        bindRoot.setData(mAssets);
        bindRoot.setCurrencyId(mCurrencyId);

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().queryCurrencyRechargeRecords(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 100, 4, "", "", "0",
                new Callback<RechargeData>() {
                    @Override
                    public void onResponse(RechargeData rechargeData) {
                        if (rechargeData != null)
                            showRecordList(rechargeData.points);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        showRecordList(null);
                    }
                });
    }

    private void showRecordList(List<RechargeData.RechargeRecord> records) {
        if (CollectionUtils.isEmpty(records))
            bindRoot.repeatView.layoutAdapterManager().showEmptyView();
        else
            bindRoot.repeatView.viewManager().bind(records);
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
        ViewUtil.gotoCustomerService(this);
    }
}