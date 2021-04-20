package com.cyberalaer.hybrid.ui.city;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class ApplyOneFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> {

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_apply_one;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                navigate(R.id.action_applyOne_to_applyTwo);
                break;
        }
    }

}