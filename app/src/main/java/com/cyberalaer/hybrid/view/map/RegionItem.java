package com.cyberalaer.hybrid.view.map;

import com.alaer.lib.api.bean.CityMaster;
import com.amap.api.maps.model.LatLng;

public class RegionItem implements ClusterItem {
    public LatLng mLatLng;
    public CityMaster cityMaster;

    public RegionItem(LatLng latLng, CityMaster cityMaster) {
        this.mLatLng = latLng;
        this.cityMaster = cityMaster;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

}
