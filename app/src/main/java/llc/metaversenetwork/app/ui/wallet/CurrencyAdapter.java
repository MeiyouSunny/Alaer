package llc.metaversenetwork.app.ui.wallet;

import com.alaer.lib.api.bean.CurrencyRecord;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemTransactionRecordBinding;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 钱包交易记录Adapter
 */
public class CurrencyAdapter extends BaseViewHolder<ItemTransactionRecordBinding, CurrencyRecord> {

    @Override
    protected void onBindData(CurrencyRecord record) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setRecord(record);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_transaction_record;
    }

}
