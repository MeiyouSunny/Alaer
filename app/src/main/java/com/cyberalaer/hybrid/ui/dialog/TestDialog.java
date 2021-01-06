package com.cyberalaer.hybrid.ui.dialog;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;

public class TestDialog extends BaseDialogHolder<FragmentLoginBinding> {

    public TestDialog() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.btnLogin.setText("xxx");
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                System.out.println("");
                break;
        }
    }

}
