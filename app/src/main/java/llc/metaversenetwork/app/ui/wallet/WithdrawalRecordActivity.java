package llc.metaversenetwork.app.ui.wallet;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.AssetsTotalInfo;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.api.bean.WithdrawalData;
import com.alaer.lib.api.bean.WithdrawalData.WithdrawalRecord;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseTitleActivity;
import llc.metaversenetwork.app.databinding.ActivityCurrencyWithdrawalRecordBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.ViewUtil;

/**
 * 币种提现记录
 */
public class WithdrawalRecordActivity extends BaseTitleActivity<ActivityCurrencyWithdrawalRecordBinding> {
    // USDT:4
    // MNC:173
    AssetsTotalInfo.Assets mAssets;
    int mCurrencyId = 4;

    @Override
    protected int titleResId() {
        return R.string.withdrawal_record;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_currency_withdrawal_record;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        setRightTitleText(R.string.call_customer_service);
        getData();
    }

    private void getData() {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().queryCurrencyWithdrawalRecords(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                1, 100, 4, "", "", "0",
                new Callback<WithdrawalData>() {
                    @Override
                    public void onResponse(WithdrawalData records) {
                        if (records != null) {
                            showRecordList(records.list);
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        showRecordList(null);
                    }
                });
    }

    private void showRecordList(List<WithdrawalRecord> records) {
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