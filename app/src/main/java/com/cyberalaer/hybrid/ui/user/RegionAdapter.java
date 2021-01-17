package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.Region;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.databinding.ItemRegionBinding;

/**
 * 地区号码Adapter
 */
public class RegionAdapter extends BaseViewHolder<ItemRegionBinding, Region> {

    @Override
    protected void onBindData(Region region) {
        bindRoot.setRegion(region);
        bindRoot.executePendingBindings();
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_region;
    }

}

