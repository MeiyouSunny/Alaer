package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistConfirmPwdBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class RegistConfirmPwdFragment extends BaseBindFragment<FragmentRegistConfirmPwdBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_regist_confirm_pwd;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopLeftIcon(R.drawable.ic_back_close);
        setTitleText(R.string.apply);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.toLogin:
                navigate(R.id.action_to_login);
                break;
        }
    }

}