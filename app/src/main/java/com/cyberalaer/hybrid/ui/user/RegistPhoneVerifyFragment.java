package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class RegistPhoneVerifyFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_phone_verify;
    }

    @Override
    public void click(View view) {
        if(view.getId() == R.id.toLogin) {
            navigate(R.id.action_to_login);
            setTitleText(R.string.login);
        }
    }

}