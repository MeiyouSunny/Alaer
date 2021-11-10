package llc.metaversenetwork.app.ui.city;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CityMasterDetail;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.meiyou.mvp.MvpBinder;

import java.util.UUID;

import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import likly.dialogger.Dialogger;
import likly.dollar.$;
import llc.metaversenetwork.app.R;
import llc.metaversenetwork.app.base.BaseBindFragment;
import llc.metaversenetwork.app.data.CityDataUtil;
import llc.metaversenetwork.app.databinding.FragmentMyCityBinding;
import llc.metaversenetwork.app.ui.App;
import llc.metaversenetwork.app.ui.dialog.DialogNotCityMaster;
import llc.metaversenetwork.app.util.ViewUtil;

@MvpBinder(
)
public class MyCityFragment extends BaseBindFragment<FragmentMyCityBinding> implements AMapLocationListener {

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
            case R.id.apply:
                applyCityNode();
                break;
        }
    }

    private void applyCityNode() {
        if (mLocation == null) {
            $.toast().text("获取定位失败！").show();
            return;
        }
        Intent intent = new Intent(getContext(), CityNodeApplyActivity.class);
        intent.putExtra("location", mLocation);
        getContext().startActivity(intent);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        try {
            activate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        TeamDetail teamDetail = UserDataUtil.instance().getTeamDetail();
        if (teamDetail != null) {
            ViewUtil.showImage(App.mAppContext, bindRoot.icHead, teamDetail.avatar);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().cityMasterInfo(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<CityMasterDetail>() {
                    @Override
                    public void onResponse(CityMasterDetail data) {
                        bindRoot.setDataUtil(new CityDataUtil());
                        bindRoot.setCityMaster(data);
                    }
                });

        ApiUtil.apiService().cityMasterStatus(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<Boolean>() {
                    @Override
                    public void onResponse(Boolean isApplyed) {
                        bindRoot.setIsApplyed(isApplyed);
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
//                getActivity().finish();
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

    // Location

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    public void activate() throws Exception {
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(5000);
            mLocationOption.setNeedAddress(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    public void deactivate() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    LatLng mLocation;

    @Override
    public void onLocationChanged(AMapLocation location) {
        if (location != null
                && location.getErrorCode() == 0) {
            final String address = location.getProvince() + location.getCity() + location.getDistrict();
            bindRoot.location.setText(address);
            mLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deactivate();
    }
}