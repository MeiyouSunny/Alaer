package llc.metaversenetwork.app.ui.user;

import com.alaer.lib.api.bean.Region;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.databinding.ItemRegionBinding;

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

