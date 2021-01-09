package com.cyberalaer.hybrid.ui.user;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.FruitBill;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemFruitBillBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 果实支出账单Adapter
 */
public class FruitBillAdapter extends RecyclerView.Adapter<FruitBillAdapter.ViewHolder> {

    List<FruitBill> data;

    public FruitBillAdapter(List<FruitBill> data) {
        this.data = data;
    }

    public void setProductList(final List<FruitBill> productList) {
        data = productList;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBillBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_fruit_bill,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setBill(data.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemFruitBillBinding binding;

        public ViewHolder(ItemFruitBillBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
