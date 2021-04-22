package com.cyberalaer.hybrid.ui.city;

import android.view.View;

import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentRegistPhoneVerifyBinding;
import com.meiyou.mvp.MvpBinder;

@MvpBinder(
)
public class MyCityFragment extends BaseBindFragment<FragmentRegistPhoneVerifyBinding> {

    UserData userData;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_my_city;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.next:
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        userData = UserDataUtil.instance().getUserData();
    }

}