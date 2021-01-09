package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthPaySuccessBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class PaySuccessFragment extends BaseBindFragment<FragmentAuthPaySuccessBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_success;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                navigate(R.id.action_to_regisPhoneVerify);
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

}