package com.cyberalaer.hybrid.ui.produce;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.entity.Produce;
import com.cyberalaer.hybrid.databinding.ProduceItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 生产基地项目Adapter
 */
public class ProduceAdapter extends RecyclerView.Adapter<ProduceAdapter.ProductViewHolder> {

    List<Produce> mProductList;

    public ProduceAdapter(List<Produce> productList) {
        mProductList = productList;
    }

    public void setProductList(final List<Produce> productList) {
        mProductList = productList;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProduceItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.produce_item,
                        parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.binding.setProduct(mProductList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductList == null ? 0 : mProductList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        final ProduceItemBinding binding;

        public ProductViewHolder(ProduceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
