package com.cyberalaer.hybrid.ui.produce;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.SeedMine;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.SeedDataUtil;
import com.cyberalaer.hybrid.databinding.ItemSeedExporedBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 过期失效Adapter
 */
public class AdapterSeedExpired extends RecyclerView.Adapter<AdapterSeedExpired.ViewHolder> {

    List<SeedMine> mData;
    private SeedDataUtil mUtil;

    public AdapterSeedExpired(List<SeedMine> data) {
        mData = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSeedExporedBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_seed_expored,
                        parent, false);

        if (mUtil == null)
            mUtil = new SeedDataUtil(parent.getResources());

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setUtil(mUtil);
        holder.binding.setSeed(mData.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemSeedExporedBinding binding;

        public ViewHolder(ItemSeedExporedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
