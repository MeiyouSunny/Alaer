package llc.metaversenetwork.app.ui.user;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.SharedUser;
import com.alaer.lib.api.bean.SharedUserDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import likly.dialogger.Dialogger;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.data.TeamLevelUtil;
import llc.metaversenetwork.app.databinding.ItemSharedUserBinding;
import llc.metaversenetwork.app.ui.dialog.DialogUserDetail;
import llc.metaversenetwork.app.util.ViewUtil;

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
        ViewUtil.showImage(context.getApplicationContext(), holder.binding.icHead, data.get(position).avatar, R.drawable.ic_app_icon);

        holder.binding.setData(data.get(position));
        holder.binding.setHandler(handler);
        holder.binding.executePendingBindings();

        showLevelStar(data.get(position), holder.binding.layoutStar);
    }

    private void showLevelStar(SharedUser sharedUser, LinearLayout layoutStar) {
        if (sharedUser.level <= 0) {
            layoutStar.removeAllViews();
            return;
        }
        for (int i = 0; i < sharedUser.level; i++) {
            ImageView icon = new ImageView(context);
            icon.setImageResource(R.drawable.ic_level_star);
            layoutStar.addView(icon);
        }
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
