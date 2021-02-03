package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.ActiveBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.databinding.ItemContributionRecordBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

/**
 * 贡献值Adapter
 */
public class ContributionAdapter extends BaseViewHolder<ItemContributionRecordBinding, ActiveBill> {

    @Override
    protected void onBindData(ActiveBill bill) {
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setBill(bill);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_contribution_record;
    }

}

