package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.alaer.lib.api.bean.UpdateInfo;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogNewVersionBinding;

public class DialogNewVersion extends BaseDialogHolder<DialogNewVersionBinding> {

    UpdateInfo updateInfo;
    OnConfirmListener listener;

    public DialogNewVersion(UpdateInfo updateInfo) {
        super(R.layout.dialog_new_version);
        this.updateInfo = updateInfo;
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        final String content = updateInfo.nowVersion + "\n" + updateInfo.msgContent;
        bindRoot.content.setText(content);
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
