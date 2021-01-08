package com.cyberalaer.hybrid.ui.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.AdTask;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemTaskBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 任务Adapter
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<AdTask> data;
    OnTaskClickHandler handler;

    public TaskAdapter(Context context, List<AdTask> data, OnTaskClickHandler handler) {
        this.data = data;
        this.handler = handler;
    }

    public void setData(List<AdTask> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_task,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setData(data.get(position));
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemTaskBinding binding;

        public ViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnTaskClickHandler {
        void onTaskClick(AdTask task);
    }

}
