package com.cyberalaer.hybrid.ui.city;

import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CityMasterDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.data.CityDataUtil;
import com.cyberalaer.hybrid.databinding.FragmentMyCityBinding;
import com.cyberalaer.hybrid.ui.dialog.DialogNotCityMaster;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.meiyou.mvp.MvpBinder;

import java.util.UUID;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import likly.dialogger.Dialogger;

@MvpBinder(
)
public class MyCityFragment extends BaseBindFragment<FragmentMyCityBinding> {

    UserData userData;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_my_city;
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cityPopulation:
                if (!isCityMaster()) {
                    showNotCityMasterDialog();
                    break;
                }
                ViewUtil.gotoActivity(getContext(), CityPopulationActivity.class);
                break;
            case R.id.customerService:
                if (!isCityMaster()) {
                    showNotCityMasterDialog();
                    break;
                }
                gotoCustomerService();
                break;
        }
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().cityMasterInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<CityMasterDetail>() {
                    @Override
                    public void onResponse(CityMasterDetail data) {
                        bindRoot.setDataUtil(new CityDataUtil());
                        bindRoot.setCityMaster(data);
                    }
                });
    }

    private boolean isCityMaster() {
        return bindRoot.getCityMaster() != null && bindRoot.getCityMaster().isMaster == 1;
    }

    private void showNotCityMasterDialog() {
        DialogNotCityMaster dialog = new DialogNotCityMaster();
        dialog.setListener(new DialogNotCityMaster.OnConfirmListener() {
            @Override
            public void onConfirmClick() {
                ViewUtil.gotoActivity(getContext(), CityNodeApplyActivity.class);
                getActivity().finish();
            }
        });
        Dialogger.newDialog(getContext()).holder(dialog)
                .gravity(Gravity.CENTER)
                .show();
    }

    private void gotoCustomerService() {
        UdeskSDKManager.getInstance().initApiKey(getContext().getApplicationContext(), AppConfig.UDESK_APP_DOMAIN,
                AppConfig.UDESK_APP_SECRETKEY, AppConfig.UDESK_APP_ID);
        final String sdkToken = UUID.randomUUID().toString();
        UdeskSDKManager.getInstance().entryChat(getContext().getApplicationContext(), UdeskConfig.createDefualt(), sdkToken);
    }

}