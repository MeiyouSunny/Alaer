package com.cyberalaer.hybrid.ui.share;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.databinding.ItemShareBinding;
import com.cyberalaer.hybrid.util.QRCodeEncoder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 邀请好友Adapter
 */
public class AdapterShare extends RecyclerView.Adapter<AdapterShare.ViewHolder> {
    private final int[] mBgRedIds = new int[]{R.drawable.img_share1, R.drawable.img_share2, R.drawable.img_share3};
    UserData userData;
    TeamDetail teamDetail;
    ShareHandler shareHandler;

    public AdapterShare(UserData userData, TeamDetail teamDetail, ShareHandler shareHandler) {
        this.userData = userData;
        this.teamDetail = teamDetail;
        this.shareHandler = shareHandler;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemShareBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_share,
                        parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setUser(userData);
        holder.binding.setTeamDetail(teamDetail);
        holder.binding.executePendingBindings();
        holder.binding.root.setBackgroundResource(mBgRedIds[position]);
        holder.binding.qrCode.setImageBitmap(QRCodeEncoder.createQRCode(AppConfig.INVITATE_URL + teamDetail.code, 160));

        holder.binding.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 核心代码start
                Bitmap bitmap = captureView(view);

                if (shareHandler != null)
                    shareHandler.onShare(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ItemShareBinding binding;

        public ViewHolder(ItemShareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private Bitmap captureView(View view) {
//        view.isDrawingCacheEnabled = true
        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache()
        view.buildDrawingCache();
        // 重新测量一遍View的宽高
        view.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY));
        // 确定View的位置
        view.layout((int) view.getX(), (int) view.getY(), (int) view.getX() + view.getMeasuredWidth(),
                (int) view.getY() + view.getMeasuredHeight());
        // 生成View宽高一样的Bitmap
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
