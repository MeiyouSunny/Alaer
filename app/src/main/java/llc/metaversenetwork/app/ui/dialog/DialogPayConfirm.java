package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogPayConfirmBinding;

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
