package llc.metaversenetwork.app.ui.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.MessageQueue;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentSharePicBinding;

import androidx.annotation.RequiresApi;

/**
 * 邀请,分享页面
 */
public class SharePicFragment extends BaseBindFragment<FragmentSharePicBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_share_pic;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated() {
        super.onViewCreated();

        Uri sharePic = getArguments().getParcelable("sharePic");
        showShareList(sharePic);
        new Handler().getLooper().getQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                bindRoot.sharePic.setImageURI(sharePic);

                return false;
            }
        });
    }

    private void showShareList(Uri sharePic) {
//        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), sharePic, "title", "desc"));
        Intent shareIntent = new Intent();
//        shareIntent.setPackage("com.tencent.mm");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, sharePic);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

}