package com.cyberalaer.hybrid.ui.notice;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.Notice;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemNoticeBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 公告Adapter
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    List<Notice> data;
    OnNoticeHandler handler;

    public NoticeAdapter(Context context, List<Notice> data) {
        this.data = data;
        handler = new OnNoticeHandler() {
            @Override
            public void clickNotice(Notice notice) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("notice", notice);
                ViewUtil.gotoActivity(context, NoticeDetailActivity.class, bundle);
            }
        };
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoticeBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_notice,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setNotice(data.get(position));
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemNoticeBinding binding;

        public ViewHolder(ItemNoticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnNoticeHandler {
        void clickNotice(Notice notice);
    }

}
