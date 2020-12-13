package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> implements View.OnClickListener {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        setListener();
    }

    private void setListener() {
        bindRoot.regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.regist:
                navigate(R.id.action_to_regist_phone_verify);
                setTitleText(R.string.regist);
                break;
        }
    }
}