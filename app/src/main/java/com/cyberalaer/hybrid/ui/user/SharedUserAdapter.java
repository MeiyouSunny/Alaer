package com.cyberalaer.hybrid.ui.user;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SharedUser;
import com.alaer.lib.api.bean.SharedUserDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.data.TeamLevelUtil;
import com.cyberalaer.hybrid.databinding.ItemSharedUserBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogUserDetail;
import com.cyberalaer.hybrid.util.ViewUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import likly.dialogger.Dialogger;

public class SharedUserAdapter extends RecyclerView.Adapter<SharedUserAdapter.ViewHolder> {

    Context context;
    List<SharedUser> data;
    TeamLevelUtil teamLevelUtil;
    Handler handler;

    public SharedUserAdapter(Context context, List<SharedUser> data) {
        this.context = context;
        this.data = data;
        handler = new Handler() {
            @Override
            public void clickUser(SharedUser user) {
                queryUserDetail(user.uuid);
            }
        };
    }

    private void queryUserDetail(String uuid) {
        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;
        ApiUtil.apiService().teamSharedUserDetail(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY, uuid,
                new Callback<SharedUserDetail>() {
                    @Override
                    public void onResponse(SharedUserDetail userDetail) {
                        showUserDetail(userDetail);
                    }
                });
    }

    private void showUserDetail(SharedUserDetail userDetail) {
        DialogUserDetail dialog = new DialogUserDetail(userDetail);
        Dialogger.newDialog(context).holder(dialog)
                .gravity(Gravity.CENTER)
                .cancelable(true)
                .show();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSharedUserBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_shared_user,
                        parent, false);

        if (teamLevelUtil == null)
            teamLevelUtil = new TeamLevelUtil(parent.getResources());

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewUtil.showImage(context.getApplicationContext(), holder.binding.icHead, data.get(position).avatar);

        holder.binding.setData(data.get(position));
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemSharedUserBinding binding;

        public ViewHolder(ItemSharedUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Handler {
        void clickUser(SharedUser user);
    }

}
