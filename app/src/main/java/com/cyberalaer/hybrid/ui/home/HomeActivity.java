package com.cyberalaer.hybrid.ui.home;

import android.app.Activity;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.Balance;
import com.alaer.lib.api.bean.TeamDetail;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityHomeBinding;
import com.cyberalaer.hybrid.ui.discover.DiscoverActivity;
import com.cyberalaer.hybrid.ui.education.EducationHallActivity;
import com.cyberalaer.hybrid.ui.government.GovernmentHallActivity;
import com.cyberalaer.hybrid.ui.government.RealNameAuthActivity;
import com.cyberalaer.hybrid.ui.produce.ProductionHallActivity;
import com.cyberalaer.hybrid.ui.travel.TravelHallActivity;
import com.cyberalaer.hybrid.ui.user.UserMineActivity;
import com.cyberalaer.hybrid.util.ViewUtil;
import com.cyberalaer.hybrid.view.mapview.MapContainer;
import com.cyberalaer.hybrid.view.mapview.Marker;
import com.meiyou.mvp.MvpBinder;

import java.util.ArrayList;
import java.util.List;

@MvpBinder(
        presenter = HomePresenterImpl.class
)
public class HomeActivity extends BaseViewBindActivity<ActivityHomeBinding> implements HomeView, MapContainer.OnMarkerClickListner {

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void showError(String content) {
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();
        initMapView();
//        initData();
    }

    private void initMapView() {
        bindRoot.map.getMapView().setImageResource(R.drawable.bg_map);
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(0.09F, 0.55F, R.string.produce_hall));
        markers.add(new Marker(0.27F, 0.46F, R.string.discover));
        markers.add(new Marker(0.4F, 0.27F, R.string.digital_cityer_auth));
        markers.add(new Marker(0.63F, 0.46F, R.string.government_hall));
        markers.add(new Marker(0.64F, 0.64F, R.string.travel_hall));
        markers.add(new Marker(0.57F, 0.88F, R.string.education_hall));
        markers.add(new Marker(0.82F, 0.35F, R.string.go_into_alaer));

        bindRoot.map.setMarkers(markers);
        bindRoot.map.setOnMarkerClickListner(this);
    }

    private Class<? extends Activity>[] mPageClasses = new Class[]{
            ProductionHallActivity.class, DiscoverActivity.class, RealNameAuthActivity.class,
            GovernmentHallActivity.class, TravelHallActivity.class, EducationHallActivity.class, TravelHallActivity.class};

    @Override
    public void onClick(View view, int position) {
        ViewUtil.gotoActivity(this, mPageClasses[position]);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.layoutUser:
                ViewUtil.gotoActivity(this, UserMineActivity.class);
                break;
        }
    }

    private void initData() {
        TeamDetail teamDetail = UserDataUtil.instance().getTeamDetail();
        if (teamDetail != null) {
            bindRoot.setUser(teamDetail);
            ViewUtil.showImage(getApplicationContext(), bindRoot.icHead, teamDetail.avatar);
        }

        UserData userData = UserDataUtil.instance().getUserData();
        if (userData == null)
            return;

        ApiUtil.apiService().getBalance(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<Balance>() {
                    @Override
                    public void onResponse(Balance balance) {
                        bindRoot.setBalance(balance);
                    }
                });
    }

}