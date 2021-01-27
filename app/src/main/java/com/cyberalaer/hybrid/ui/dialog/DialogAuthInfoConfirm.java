package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogAuthInfoConfirmBinding;

/**
 * 认证信息确认
 */
public class DialogAuthInfoConfirm extends BaseDialogHolder<DialogAuthInfoConfirmBinding> {

    String name;
    String cardNo;
    OnConfirmListener listener;

    public DialogAuthInfoConfirm(String name, String cardNo) {
        super(R.layout.dialog_auth_info_confirm);
        this.name = name;
        this.cardNo = cardNo;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.name.setText(getContext().getResources().getString(R.string.name_is, name));
        bindRoot.cardNo.setText(getContext().getResources().getString(R.string.card_no_is, cardNo));
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.confirm:
                if (listener != null)
                    listener.onConfirmClick();
                dismiss();
                break;
        }
    }

    public interface OnConfirmListener {
        void onConfirmClick();
    }

}
