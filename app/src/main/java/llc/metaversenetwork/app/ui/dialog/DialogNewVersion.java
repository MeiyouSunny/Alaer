package llc.metaversenetwork.app.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.UpdateInfo;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogNewVersionBinding;

public class DialogNewVersion extends BaseDialogHolder<DialogNewVersionBinding> {

    UpdateInfo updateInfo;
    boolean forceUpdate;
    OnConfirmListener listener;

    public DialogNewVersion(UpdateInfo updateInfo, boolean forceUpdate) {
        super(R.layout.dialog_new_version);
        this.updateInfo = updateInfo;
        this.forceUpdate = false;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        final String content = updateInfo.nowVersion + "\n" + updateInfo.msgContent;
        bindRoot.content.setText(content);

        if (forceUpdate) {
            bindRoot.cancel.setVisibility(View.GONE);
            bindRoot.divider.setVisibility(View.GONE);
        }
    }

    public void setListener(OnConfirmListener listener) {
        this.listener = listener;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                if (listener != null)
                    listener.onCancelClick();
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
        void onCancelClick();
        void onConfirmClick();
    }

}
