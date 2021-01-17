package com.cyberalaer.hybrid.ui.produce;

import android.view.View;

import com.alaer.lib.api.bean.SeedStore;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.data.SeedDataUtil;
import com.cyberalaer.hybrid.databinding.ItemSeedStoreBinding;

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
