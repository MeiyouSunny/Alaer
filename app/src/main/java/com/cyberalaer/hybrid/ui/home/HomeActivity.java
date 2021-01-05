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
import com.cyberalaer.hybrid.ui.education.EducationHallActivity;
import com.cyberalaer.hybrid.ui.game.GameHallActivity;
import com.cyberalaer.hybrid.ui.government.GovernmentHallActivity;
import com.cyberalaer.hybrid.ui.hospital.HospitalActivity;
import com.cyberalaer.hybrid.ui.leisure.LeisureHallActivity;
import com.cyberalaer.hybrid.ui.produce.ProductionHallActivity;
import com.cyberalaer.hybrid.ui.shopping.DigitalMallActivity;
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
        initData();
    }

    private void initMapView() {
        bindRoot.map.getMapView().setImageResource(R.drawable.bg_map);
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(0.61F, 0.35F, R.string.education_hall));
        markers.add(new Marker(0.89F, 0.33F, R.string.digital_mall));
        markers.add(new Marker(0.53F, 0.45F, R.string.government_hall));
        markers.add(new Marker(0.85F, 0.63F, R.string.travel_hall));
        markers.add(new Marker(0.58F, 0.64f, R.string.game_hall));
        markers.add(new Marker(0.34F, 0.6F, R.string.people_hospital));
        markers.add(new Marker(0.38F, 0.81F, R.string.leisure_hall));
        markers.add(new Marker(0.21F, 0.48F, R.string.produce_hall));
        bindRoot.map.setMarkers(markers);
        bindRoot.map.setOnMarkerClickListner(this);
    }

    private Class<? extends Activity>[] mPageClasses = new Class[]{EducationHallActivity.class, DigitalMallActivity.class, GovernmentHallActivity.class,
            TravelHallActivity.class, GameHallActivity.class, HospitalActivity.class, LeisureHallActivity.class, ProductionHallActivity.class};

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