package com.cyberalaer.hybrid.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.entity.Produce;
import com.cyberalaer.hybrid.databinding.ActiveDetailItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 活跃度明细Adapter
 */
public class ActiveDetailAdapter extends RecyclerView.Adapter<ActiveDetailAdapter.ViewHolder> {

    List<Produce> mProductList;

    public ActiveDetailAdapter(List<Produce> productList) {
        mProductList = productList;
    }

    public void setProductList(final List<Produce> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActiveDetailItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.active_detail_item,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setProduct(mProductList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ActiveDetailItemBinding binding;

        public ViewHolder(ActiveDetailItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
