package com.cyberalaer.hybrid.ui.produce;

import android.view.View;

import com.alaer.lib.api.bean.SeedMine;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.repeatview.BaseViewHolder;
import com.cyberalaer.hybrid.data.SeedDataUtil;
import com.cyberalaer.hybrid.databinding.ItemSeedExporedBinding;

/**
 * 过期失效Adapter
 */
public class AdapterSeedExpired extends BaseViewHolder<ItemSeedExporedBinding, SeedMine> {

    //    List<SeedMine> mData;
    private SeedDataUtil mUtil;

//    public AdapterSeedExpired(List<SeedMine> data) {
//        mData = data;
//    }

//    @Override
//    @NonNull
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ItemSeedExporedBinding binding = DataBindingUtil
//                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_seed_expored,
//                        parent, false);
//
//        if (mUtil == null)
//            mUtil = new SeedDataUtil(parent.getResources());
//
//        return new ViewHolder(binding);
//    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.binding.setUtil(mUtil);
//        holder.binding.setSeed(mData.get(position));
//        holder.binding.executePendingBindings();
//    }

//    @Override
//    public int getItemCount() {
//        return mData == null ? 0 : mData.size();
//    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mUtil = new SeedDataUtil(view.getResources());
    }

    @Override
    protected int getViewHolderLayout() {
        return R.layout.item_seed_expored;
    }

    @Override
    protected void onBindData(SeedMine data) {
        bindRoot.setUtil(mUtil);
        bindRoot.setSeed(data);
        bindRoot.executePendingBindings();
    }

    //    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        final ItemSeedExporedBinding binding;
//
//        public ViewHolder(ItemSeedExporedBinding binding) {
//            super(binding.getRoot());
//            this.binding = binding;
//        }
//    }
}
