package com.cyberalaer.hybrid.ui.city;

import android.os.Bundle;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.amap.api.maps.model.LatLng;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityAuthBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.Nullable;

/**
 * 城市节点申请页面
 */
@MvpBinder(
)
public class CityNodeApplyActivity extends BaseTitleActivity<ActivityAuthBinding> {
    public LatLng mLocation;

    @Override
    protected int titleResId() {
        return R.string.city_note_apply;
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_city_note_apply;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocation = getIntent().getParcelableExtra("location");
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().uploadLocation(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                mLocation.longitude, mLocation.latitude,
                new Callback<String>() {
                    @Override
                    public void onResponse(String isApplyed) {
                        super.onResponse(isApplyed);
                    }
                });
    }

}