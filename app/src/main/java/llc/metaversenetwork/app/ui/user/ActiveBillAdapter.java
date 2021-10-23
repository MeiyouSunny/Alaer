package llc.metaversenetwork.app.ui.user;

import android.view.View;

import com.alaer.lib.api.bean.ActiveBill;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.data.BillUtil;
import llc.metaversenetwork.app.databinding.ItemActiveBillBinding;
import llc.metaversenetwork.app.util.NumberUtils;

/**
 * 活跃度账单Adapter
 */
public class ActiveBillAdapter extends BaseViewHolder<ItemActiveBillBinding, ActiveBill> {

    BillUtil billUtil;

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        if (billUtil == null)
            billUtil = new BillUtil();
    }

    @Override
    protected void onBindData(ActiveBill bill) {
        bindRoot.setBillUtil(billUtil);
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBill(bill);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_active_bill;
    }

}
