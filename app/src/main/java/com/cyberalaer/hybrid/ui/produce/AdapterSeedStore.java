package com.cyberalaer.hybrid.ui.produce;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.SeedStore;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.SeedDataUtil;
import com.cyberalaer.hybrid.databinding.ItemSeedStoreBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 种子商店Adapter
 */
public class AdapterSeedStore extends RecyclerView.Adapter<AdapterSeedStore.ViewHolder> {

    List<SeedStore> mData;
    private SeedDataUtil mUtil;
    private boolean claimNewbieMiner;
    private OnBuySeedHandler handler;

    public AdapterSeedStore(List<SeedStore> data, boolean claimNewbieMiner, OnBuySeedHandler handler) {
        this.mData = data;
        this.claimNewbieMiner = claimNewbieMiner;
        this.handler = handler;
    }

    public void setmData(List<SeedStore> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSeedStoreBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_seed_store,
                        parent, false);

        if (mUtil == null)
            mUtil = new SeedDataUtil(parent.getResources());

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setUtil(mUtil);
        holder.binding.setSeed(mData.get(position));
        holder.binding.setClaimNewbieMiner(claimNewbieMiner);
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemSeedStoreBinding binding;

        public ViewHolder(ItemSeedStoreBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnBuySeedHandler {
        void onBuySeed(SeedStore seed);
    }
}
