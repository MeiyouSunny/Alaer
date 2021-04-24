package com.cyberalaer.hybrid.ui.city;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.alaer.lib.api.ApiUtil;
import com.alaer.lib.api.AppConfig;
import com.alaer.lib.api.Callback;
import com.alaer.lib.api.bean.CityMaster;
import com.alaer.lib.api.bean.UserData;
import com.alaer.lib.data.UserDataUtil;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseBindFragment;
import com.cyberalaer.hybrid.databinding.FragmentCityHallBinding;
import com.cyberalaer.hybrid.view.map.ClusterClickListener;
import com.cyberalaer.hybrid.view.map.ClusterItem;
import com.cyberalaer.hybrid.view.map.ClusterOverlay;
import com.cyberalaer.hybrid.view.map.ClusterRender;
import com.cyberalaer.hybrid.view.map.RegionItem;
import com.meiyou.mvp.MvpBinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MvpBinder(
)
public class CityHallFragment extends BaseBindFragment<FragmentCityHallBinding> implements ClusterRender,
        AMap.OnMapLoadedListener, ClusterClickListener {

    private TextureMapView mMapView;
    private AMap mAMap;

    @Override
    public int initLayoutResId() {
        return R.layout.fragment_city_hall;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindRoot.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        bindRoot.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bindRoot.mapView.onDestroy();
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

        bindRoot.mapView.onCreate(getArguments());
        mAMap = bindRoot.mapView.getMap();
//        mAMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        queryCityMasters();
    }

    private void queryCityMasters() {
        UserData userData = UserDataUtil.instance().getUserData();
        ApiUtil.apiService().cityMasters(userData.uuid, String.valueOf(userData.uid), userData.token, AppConfig.DIAMOND_CURRENCY,
                new Callback<List<CityMaster>>() {
                    @Override
                    public void onResponse(List<CityMaster> cityMasters) {
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showCityMasters(cityMasters);
                            }
                        }, 2000);
                    }
                });
    }

    private void showCityMasters(List<CityMaster> cityMasters) {
        new Thread() {
            public void run() {

                List<ClusterItem> items = new ArrayList<ClusterItem>();

                for (CityMaster cityMaster : cityMasters) {
                    LatLng latLng = new LatLng(cityMaster.lat, cityMaster.lon, false);
                    RegionItem regionItem = new RegionItem(latLng, cityMaster);
                    items.add(regionItem);
                }
                ClusterOverlay mClusterOverlay = new ClusterOverlay(mAMap, items,
                        dp2px(getContext().getApplicationContext(), 30),
                        getContext().getApplicationContext());
                mClusterOverlay.setClusterRenderer(CityHallFragment.this);
                mClusterOverlay.setOnClusterClickListener(CityHallFragment.this);

            }

        }.start();
    }

    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();

    @Override
    public Drawable getDrawAble(int clusterNum) {
        int radius = dp2px(getContext().getApplicationContext(), 55);
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            if (bitmapDrawable == null) {
                bitmapDrawable = getContext().getResources().getDrawable(
                        R.drawable.icon_openmap_mark);
                mBackDrawAbles.put(1, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 5) {

            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(220, 210, 154, 6)));
                mBackDrawAbles.put(2, bitmapDrawable);
            }

            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(220, 217, 114, 0)));
                mBackDrawAbles.put(3, bitmapDrawable);
            }

            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = new BitmapDrawable(null, drawCircle(radius,
                        Color.argb(220, 215, 66, 2)));
                mBackDrawAbles.put(4, bitmapDrawable);
            }

            return bitmapDrawable;
        }
    }

    private Bitmap drawCircle(int radius, int color) {

        Bitmap bitmap = Bitmap.createBitmap(radius * 2, radius * 2,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        paint.setColor(color);
        canvas.drawArc(rectF, 0, 360, true, paint);
        return bitmap;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());
        }
        LatLngBounds latLngBounds = builder.build();
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
    }

}