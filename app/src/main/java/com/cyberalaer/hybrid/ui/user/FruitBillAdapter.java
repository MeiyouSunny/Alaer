package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.FruitBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.databinding.ItemFruitBillBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

/**
 * 果实支出账单Adapter
 */
public class FruitBillAdapter extends BaseViewHolder<ItemFruitBillBinding, FruitBill> {

    @Override
    protected void onBindData(FruitBill bill) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBill(bill);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_fruit_bill;
    }

}
