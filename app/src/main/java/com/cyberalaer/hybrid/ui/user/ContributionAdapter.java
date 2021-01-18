package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.ActiveBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.databinding.ItemContributionRecordBinding;

/**
 * 贡献值Adapter
 */
public class ContributionAdapter extends BaseViewHolder<ItemContributionRecordBinding, ActiveBill> {

    @Override
    protected void onBindData(ActiveBill region) {
        bindRoot.setBill(region);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_contribution_record;
    }

}

