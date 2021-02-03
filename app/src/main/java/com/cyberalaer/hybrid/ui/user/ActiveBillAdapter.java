package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.alaer.lib.api.bean.ActiveBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.data.BillUtil;
import com.cyberalaer.hybrid.databinding.ItemActiveBillBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

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
