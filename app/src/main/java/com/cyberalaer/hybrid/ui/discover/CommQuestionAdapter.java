package com.cyberalaer.hybrid.ui.discover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.CommonQuestion;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemCommQuestionBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 常见问题Adapter
 */
public class CommQuestionAdapter extends RecyclerView.Adapter<CommQuestionAdapter.ViewHolder> {

    Context context;
    List<CommonQuestion> data;
    OnQuestionHandler handler;

    public CommQuestionAdapter(Context context, List<CommonQuestion> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommQuestionBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comm_question,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (handler == null)
            handler = new OnQuestionHandler() {
                @Override
                public void onQuestionClick(CommonQuestion question) {
                    gotoWebPage(question);
                }
            };
        holder.binding.setHandler(handler);
        holder.binding.setQuestion(data.get(position));
        holder.binding.executePendingBindings();

        ViewUtil.showImage(context, holder.binding.icon, data.get(position).image);
    }

    private void gotoWebPage(CommonQuestion question) {
        WebPageActivity.start(context, question.path, question.title);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemCommQuestionBinding binding;

        public ViewHolder(ItemCommQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnQuestionHandler {
        void onQuestionClick(CommonQuestion question);
    }

}
