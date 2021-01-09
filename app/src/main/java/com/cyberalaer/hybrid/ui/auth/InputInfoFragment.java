package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthInputInfoBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class InputInfoFragment extends BaseBindFragment<FragmentAuthInputInfoBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_input_info;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                navigate(R.id.action_to_pay_confirm);
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
//        bindRoot.etPhone.addTextChangedListener(new SimpleTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                onInputChange();
//            }
//        });
//        bindRoot.etPwd.addTextChangedListener(new SimpleTextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                onInputChange();
//            }
//        });
    }

    private void onInputChange() {
//        bindRoot.next.setEnabled(hasInput);
    }

}