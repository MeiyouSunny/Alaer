package llc.metaversenetwork.app.ui.wallet;

import android.os.Build;

import com.alaer.lib.api.bean.CoinContract;
import com.alaer.lib.api.bean.WithdrawalData.WithdrawalRecord;

import java.util.List;

import androidx.annotation.RequiresApi;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemWithdrawalRecordBinding;
import llc.metaversenetwork.app.util.CollectionUtils;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 钱包提现记录
 */
public class WithdrawalAdapter extends BaseViewHolder<ItemWithdrawalRecordBinding, WithdrawalRecord> {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onBindData(WithdrawalRecord record) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setRecord(record);
        bindRoot.contractType.setText(getContext().getString(R.string.coin_contract_type_is, parseContractType(record)));
        // 0. 待充值 1. 充值中 2. 充值成功 3. 充值失败
        String[] statusArray = getContext().getResources().getStringArray(R.array.recharge_status);
        if (record.status < statusArray.length) {
            bindRoot.status.setText(statusArray[record.status]);
            int color = getContext().getColor(R.color.green2);
            if (record.status == 0 || record.status == 1)
                color = getContext().getColor(R.color.font_orange);
            if (record.status == 3)
                color = getContext().getColor(R.color.font_red);
            bindRoot.status.setTextColor(color);
        }
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_withdrawal_record;
    }

    private String parseContractType(WithdrawalRecord record) {
        List<CoinContract> contracts = WithdrawalActivity.mCoinContracts;
        if (CollectionUtils.isEmpty(contracts) || record == null)
            return "";
        for (CoinContract contract : contracts) {
            if (contract.contractId == record.contractId)
                return contract.contract;
        }
        return "";
    }

}
