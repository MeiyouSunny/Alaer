package com.cyberalaer.hybrid.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityHomeBinding;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initMapView();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void showError(String content) {
    }

    private void initMapView() {
        bindRoot.map.getMapView().setImageResource(R.drawable.bg_map);
        List<Marker> markers = new ArrayList<>();
        markers.add(new Marker(0.61F, 0.35F, R.drawable.icon_education));
        markers.add(new Marker(0.89F, 0.33F, R.drawable.icon_digital_mall));
        markers.add(new Marker(0.53F, 0.45F, R.drawable.icon_government));
        markers.add(new Marker(0.85F, 0.63F, R.drawable.icon_travel));
        markers.add(new Marker(0.58F, 0.64f, R.drawable.icon_game));
        markers.add(new Marker(0.34F, 0.6F, R.drawable.icon_hospital));
        markers.add(new Marker(0.38F, 0.81F, R.drawable.icon_leisure));
        markers.add(new Marker(0.21F, 0.48F, R.drawable.icon_produce));
        bindRoot.map.setMarkers(markers);
        bindRoot.map.setOnMarkerClickListner(this);
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

}