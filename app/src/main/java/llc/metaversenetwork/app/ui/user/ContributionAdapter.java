package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.bean.ActiveBill;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.data.BillUtil;
import llc.metaversenetwork.app.databinding.ItemContributionRecordBinding;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 贡献值Adapter
 */
public class ContributionAdapter extends BaseViewHolder<ItemContributionRecordBinding, ActiveBill> {

    @Override
    protected void onBindData(ActiveBill bill) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBillUtil(new BillUtil());
        bindRoot.setBill(bill);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_contribution_record;
    }

}

