package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthPayConfirmBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class PayConfirmFragment extends BaseBindFragment<FragmentAuthPayConfirmBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_confirm;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.confirm:
                navigate(R.id.action_to_pay_success);
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
    }

}