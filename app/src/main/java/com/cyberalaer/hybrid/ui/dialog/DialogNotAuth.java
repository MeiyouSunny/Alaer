package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogNotAuthBinding;

/**
 * 未实名认证
 */
public class DialogNotAuth extends BaseDialogHolder<DialogNotAuthBinding> {

    OnConfirmListener listener;

    public DialogNotAuth() {
        super(R.layout.dialog_not_auth);
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
