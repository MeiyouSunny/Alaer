package llc.metaversenetwork.app.ui.produce;

import android.view.View;

import com.alaer.lib.api.bean.SeedMine;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.data.SeedDataUtil;
import llc.metaversenetwork.app.databinding.ItemSeedMineBinding;

/**
 * 我的种子Adapter
 */
public class AdapterSeedMine extends BaseViewHolder<ItemSeedMineBinding, SeedMine> {

    SeedDataUtil mUtil;

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_seed_mine;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mUtil = new SeedDataUtil(view.getResources());
    }

    @Override
    protected void onBindData(SeedMine data) {
        bindRoot.setSeed(data);
        bindRoot.setUtil(mUtil);
        bindRoot.executePendingBindings();
    }

}
