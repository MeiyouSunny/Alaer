package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogPayConfirmBinding;

/**
 * 支付确认
 */
public class DialogPayConfirm extends BaseDialogHolder<DialogPayConfirmBinding> {

    OnConfirmListener listener;

    public DialogPayConfirm() {
        super(R.layout.dialog_pay_confirm);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
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
