package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogBuySeedSuccessBinding;

public class DialogBuySeedSuccess extends BaseDialogHolder<DialogBuySeedSuccessBinding> {

    String seedName;
    OnConfirmListener listener;

    public DialogBuySeedSuccess(String seedName) {
        super(R.layout.dialog_buy_seed_success);
        this.seedName = seedName;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.content.setText(getContext().getResources().getString(R.string.seed_buyed_is, seedName));
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
