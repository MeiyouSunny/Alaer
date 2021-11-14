package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.WithdrawConfirmInfo;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogWithdrawInfoConfirmBinding;

/**
 * 认证信息确认
 */
public class DialogWithdrawInfoConfirm extends BaseDialogHolder<DialogWithdrawInfoConfirmBinding> {
    WithdrawConfirmInfo confirmInfo;
    OnConfirmListener listener;

    public DialogWithdrawInfoConfirm(WithdrawConfirmInfo confirmInfo) {
        super(R.layout.dialog_withdraw_info_confirm);
        this.confirmInfo = confirmInfo;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.setConfirmInfo(confirmInfo);
        bindRoot.executePendingBindings();
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
            case R.id.submit:
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
