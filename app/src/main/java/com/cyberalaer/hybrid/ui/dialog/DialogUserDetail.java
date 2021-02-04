package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.SharedUserDetail;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogSharedUserDetailBinding;
import com.cyberalaer.hybrid.util.NumberUtils;

public class DialogUserDetail extends BaseDialogHolder<DialogSharedUserDetailBinding> {

    SharedUserDetail userDetail;

    public DialogUserDetail(SharedUserDetail userDetail) {
        super(R.layout.dialog_shared_user_detail);
        this.userDetail = userDetail;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
//        bindRoot.setStringUtil(new StringUtil());
        bindRoot.setNumber(NumberUtils.instance());
        bindRoot.setData(userDetail);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }

}
