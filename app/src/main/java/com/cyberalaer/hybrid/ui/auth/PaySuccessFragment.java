package com.cyberalaer.hybrid.ui.auth;

import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentAuthPaySuccessBinding;
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
                ViewUtil.gotoActivity(getContext(), AuthSuccessActivity.class);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        refreshProfile();
    }

    private void refreshProfile() {
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().getTeamDetailInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<TeamDetail>() {
                    @Override
                    public void onResponse(TeamDetail teamDetail) {
                        UserDataUtil.instance().saveTeamDetailInfo(teamDetail);
                    }
                });
    }

}