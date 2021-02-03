package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthPayFailedBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class PayFailedFragment extends BaseBindFragment<FragmentAuthPayFailedBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_auth_pay_failed;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ok:
//                navigate(R.id.action_to_input_page);
                getActivity().onBackPressed();
                break;
        }
    }

}