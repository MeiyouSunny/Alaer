package com.cyberalaer.hybrid.ui.user;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentResetPwdBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class RestPwdFragment extends BaseBindFragment<FragmentResetPwdBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_reset_pwd;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTopLeftIcon(R.drawable.ic_back_black);
        setTitleText(R.string.reset_pwd);
    }

    @Override
    public void click(View view) {
    }

}