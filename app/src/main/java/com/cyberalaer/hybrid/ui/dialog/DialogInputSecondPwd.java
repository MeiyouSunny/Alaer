package com.cyberalaer.hybrid.ui.dialog;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogInputSecondPwdBinding;
import com.cyberalaer.hybrid.util.SimpleTextWatcher;
import com.cyberalaer.hybrid.util.ViewUtil;

public class DialogInputSecondPwd extends BaseDialogHolder<DialogInputSecondPwdBinding> {

    OnConfirmListener listener;

    public DialogInputSecondPwd() {
        super(R.layout.dialog_input_second_pwd);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);

        bindRoot.pwd.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                boolean hasInput = !TextUtils.isEmpty(ViewUtil.getText(bindRoot.pwd));
                bindRoot.confirm.setEnabled(hasInput);
            }
        });
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
                    listener.onConfirmClick(ViewUtil.getText(bindRoot.pwd));
                dismiss();
                break;
        }
    }

    public interface OnConfirmListener {
        void onConfirmClick(String pwd);
    }

}
