package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class RegistPhoneVerifyFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> implements View.OnClickListener {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_phone_verify;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setListener();
    }

    private void setListener() {
        bindRoot.toLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toLogin:
                navigate(R.id.action_to_login);
                setTitleText(R.string.login);
                break;
        }
    }

}