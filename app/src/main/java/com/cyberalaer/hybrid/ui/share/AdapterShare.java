package com.cyberalaer.hybrid.ui.share;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemShareBinding;
import com.cyberalaer.hybrid.util.ThreadUtil;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 邀请好友Adapter
 */
public class AdapterShare extends RecyclerView.Adapter<AdapterShare.ViewHolder> {
    private final int[] mBgRedIds = new int[]{R.drawable.img_share1, R.drawable.img_share2, R.drawable.img_share3,
            R.drawable.img_share4, R.drawable.img_share5, R.drawable.img_share6};
    UserData userData;
    TeamDetail teamDetail;
    ShareHandler shareHandler;
    Bitmap qrCode;

    public AdapterShare(UserData userData, TeamDetail teamDetail, ShareHandler shareHandler) {
        this.userData = userData;
        this.teamDetail = teamDetail;
        this.shareHandler = shareHandler;
    }

    public void refreshQrCode(Bitmap qrCode) {
        this.qrCode = qrCode;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShareBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_share,
                        parent, false);

        return new ViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setUser(userData);
        holder.binding.setTeamDetail(teamDetail);
        holder.binding.executePendingBindings();

        ThreadUtil.runIdle(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                holder.binding.root.setBackgroundResource(mBgRedIds[position]);
                return false;
            }
        });

        if (qrCode != null)
            holder.binding.qrCode.setImageBitmap(qrCode);

        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = captureView(view);

                if (shareHandler != null)
                    shareHandler.onShare(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemShareBinding binding;

        public ViewHolder(ItemShareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private Bitmap captureView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(),
                (int) view.getY() + view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache(), 0, 0, view.getMeasuredWidth(),
                view.getMeasuredHeight());
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return bitmap;
    }

    public interface ShareHandler {
        void onShare(Bitmap bitmap);
    }

}
