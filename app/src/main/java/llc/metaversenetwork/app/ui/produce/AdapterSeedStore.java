package llc.metaversenetwork.app.ui.produce;

import android.view.View;

import com.alaer.lib.api.bean.SeedStore;
import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.repeatview.BaseViewHolder;
import llc.metaversenetwork.app.data.SeedDataUtil;
import llc.metaversenetwork.app.databinding.ItemSeedStoreBinding;

/**
 * 种子商店Adapter
 */
public class AdapterSeedStore extends BaseViewHolder<ItemSeedStoreBinding, SeedStore> {

    boolean claimNewbieMiner;
    SeedDataUtil mUtil;

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_seed_store;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mUtil = new SeedDataUtil(view.getResources());
        claimNewbieMiner = UserDataUtil.instance().isClaimNewbieMiner();
    }

    @Override
    protected void onBindData(SeedStore data) {
        bindRoot.setSeed(data);
        bindRoot.setUtil(mUtil);
        bindRoot.setClaimNewbieMiner(claimNewbieMiner);
        bindRoot.executePendingBindings();
    }
}
