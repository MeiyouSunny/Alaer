package com.cyberalaer.hybrid.ui.share;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentShareListBinding;
import com.cyberalaer.hybrid.util.QRCodeEncoder;
import com.cyberalaer.hybrid.util.ThreadUtil;
import com.cyberalaer.hybrid.view.GradViewItemDecoration;

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

}