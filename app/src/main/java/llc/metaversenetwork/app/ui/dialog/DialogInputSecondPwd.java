package llc.metaversenetwork.app.ui.dialog;

import android.text.Editable;
import android.text.TextUtils;
import android.view.View;

import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseDialogHolder;
import llc.metaversenetwork.app.databinding.DialogInputSecondPwdBinding;
import llc.metaversenetwork.app.ui.user.SecondPwdSetActivity;
import llc.metaversenetwork.app.util.SimpleTextWatcher;
import llc.metaversenetwork.app.util.ViewUtil;

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
            case R.id.forgetPwd:
                ViewUtil.gotoActivity(getContext(), SecondPwdSetActivity.class);
                dismiss();
                break;
        }
    }

    public interface OnConfirmListener {
        void onConfirmClick(String pwd);
    }

}
