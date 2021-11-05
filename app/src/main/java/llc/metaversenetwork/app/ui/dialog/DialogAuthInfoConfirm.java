package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogAuthInfoConfirmBinding;

/**
 * 认证信息确认
 */
public class DialogAuthInfoConfirm extends BaseDialogHolder<DialogAuthInfoConfirmBinding> {

    String cardType;
    String name;
    String cardNo;
    OnConfirmListener listener;

    public DialogAuthInfoConfirm(String cardType, String name, String cardNo) {
        super(R.layout.dialog_auth_info_confirm);
        this.cardType = cardType;
        this.name = name;
        this.cardNo = cardNo;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.cardType.setText(cardType);
        bindRoot.name.setText(name);
        bindRoot.cardNo.setText(cardNo);
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
