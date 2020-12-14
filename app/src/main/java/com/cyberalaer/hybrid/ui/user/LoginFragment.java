package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentLoginBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class LoginFragment extends BaseBindFragment<FragmentLoginBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void click(View view) {
        if (view.getId() == R.id.toRegist) {
            navigate(R.id.action_to_regist_phone_verify);
            setTitleText(R.string.regist);
        }
    }

}