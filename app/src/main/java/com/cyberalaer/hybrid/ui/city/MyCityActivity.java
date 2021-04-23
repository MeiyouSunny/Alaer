package com.cyberalaer.hybrid.ui.city;

import android.view.View;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseViewBindActivity;
import com.cyberalaer.hybrid.databinding.ActivityMyCityBinding;

import androidx.fragment.app.Fragment;

/**
 * 我的城市/城市大厅
 */
public class MyCityActivity extends BaseViewBindActivity<ActivityMyCityBinding> {

    private CityHallFragment mFragmentCityHall;
    private MyCityFragment mFragmentMyCity;

    @Override
    protected int layoutId() {
        return R.layout.activity_my_city;
    }

    @Override
    public void onViewCreated() {
        mFragmentCityHall = new CityHallFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, mFragmentCityHall)
                .commit();
    }

    private void showFragment(Fragment fragment) {
        if (mFragmentMyCity == null) {
            mFragmentMyCity = new MyCityFragment();
            fragment = mFragmentMyCity;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
        boolean showCityHall = (fragment == mFragmentCityHall);
        bindRoot.iconCityHall.setImageResource(showCityHall ? R.drawable.ic_city_point_select : R.drawable.ic_city_point_unselect);
        bindRoot.iconMyCity.setImageResource(showCityHall ? R.drawable.ic_city_point_unselect : R.drawable.ic_city_point_select);
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cityHall:
                showFragment(mFragmentCityHall);
                break;
            case R.id.myCity:
                showFragment(mFragmentMyCity);
                break;
        }
    }

}