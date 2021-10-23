package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogBuySeedSuccessBinding;

public class DialogBuySeedSuccess extends BaseDialogHolder<DialogBuySeedSuccessBinding> {

    String getType;
    String seedName;
    OnConfirmListener listener;

    public DialogBuySeedSuccess(String getType, String seedName) {
        super(R.layout.dialog_buy_seed_success);
        this.getType = getType;
        this.seedName = seedName;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.title.setText(getType);
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
