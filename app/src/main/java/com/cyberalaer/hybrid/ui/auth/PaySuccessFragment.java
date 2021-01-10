package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthPaySuccessBinding;
import com.cyberalaer.hybrid.ui.share.ShareActivity;
import com.cyberalaer.hybrid.util.ViewUtil;
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
            case R.id.ok:
                ViewUtil.gotoActivity(getContext(), ShareActivity.class);
                getActivity().finish();
                break;
        }
    }

}