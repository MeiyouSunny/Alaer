package com.cyberalaer.hybrid.ui.user;

import com.alaer.lib.api.bean.TeamDetail;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityInviterInfoBinding;
import com.cyberalaer.hybrid.util.ViewUtil;

/**
 * 邀请人信息
 */
public class InviterInfoActivity extends BaseTitleActivity<ActivityInviterInfoBinding> {

    @Override
    protected int titleResId() {
        return R.string.inviter_info;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_inviter_info;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        TeamDetail info = (TeamDetail) getIntent().getSerializableExtra("inviter");
        if (info == null)
            return;

        bindRoot.setData(info);
        ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, info.avatar);
    }

}