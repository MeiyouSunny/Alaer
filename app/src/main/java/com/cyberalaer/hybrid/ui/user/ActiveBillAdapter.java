package com.cyberalaer.hybrid.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.ActiveBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemActiveBillBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 活跃度账单Adapter
 */
public class ActiveBillAdapter extends RecyclerView.Adapter<ActiveBillAdapter.ViewHolder> {

    List<ActiveBill> data;

    public ActiveBillAdapter(List<ActiveBill> data) {
        this.data = data;
    }

    public void setProductList(final List<ActiveBill> productList) {
        data = productList;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemActiveBillBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_active_bill,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setNumber(NumberUtils.instance());
        holder.binding.setBill(data.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemActiveBillBinding binding;

        public ViewHolder(ItemActiveBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
