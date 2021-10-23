package llc.metaversenetwork.app.ui.share;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import com.alaer.lib.data.UserDataUtil;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.databinding.FragmentShareListBinding;
import llc.metaversenetwork.app.util.QRCodeEncoder;
import llc.metaversenetwork.app.util.ThreadUtil;
import llc.metaversenetwork.app.view.GradViewItemDecoration;

import likly.dollar.$;

/**
 * 邀请,分享列表
 */
public class ShareListFragment extends BaseBindFragment<FragmentShareListBinding> implements AdapterShare.ShareHandler {

    private AdapterShare adapter;

    public static ShareListFragment newInstance() {
        ShareListFragment fragment = new ShareListFragment();
        return fragment;
    }

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_share_list;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        showList();
    }

    private void showList() {
        adapter = new AdapterShare(UserDataUtil.instance().getUserData(), UserDataUtil.instance().getTeamDetail(), this);
        bindRoot.list.addItemDecoration(new GradViewItemDecoration(getContext(), 4));
        bindRoot.list.setAdapter(adapter);

        ThreadUtil.runThread(() -> {
            final Bitmap qrCode = QRCodeEncoder.createQRCode(UserDataUtil.instance().getTeamDetail().inviteUrl, 100);
            ThreadUtil.runUIThread(() -> adapter.refreshQrCode(qrCode));
        });

    }

    @Override
    public void onShare(Bitmap sharePic) {
        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), sharePic, "Alaer", "InvitateCode"));
        Bundle data = new Bundle();
        data.putParcelable("sharePic", imageUri);
        navigate(R.id.action_to_share_pic, data);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.copyCode:
                copy(UserDataUtil.instance().getTeamDetail().code);
                break;
            case R.id.copyUrl:
                copy(UserDataUtil.instance().getTeamDetail().inviteUrl);
                break;
        }
    }

    public void copy(String content) {
        if (!TextUtils.isEmpty(content)) {
            ClipboardManager cmb = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content.trim());
            ClipData clipData = ClipData.newPlainText(null, content);
            cmb.setPrimaryClip(clipData);
            $.toast().text("复制成功").show();
        }
    }

}