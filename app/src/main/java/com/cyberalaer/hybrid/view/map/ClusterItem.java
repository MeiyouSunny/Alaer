package com.cyberalaer.hybrid.view.map;

import com.amap.api.maps.model.LatLng;

public interface ClusterItem {
    /**
     * 返回聚合元素的地理位置
     *
     * @return
     */
    LatLng getPosition();
}
