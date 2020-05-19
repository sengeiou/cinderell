package com.cinderellavip.map;

import com.amap.api.location.AMapLocation;

public interface OnLocationListener {
    void onSuccess(AMapLocation aMapLocation, double lat, double lnt);
}
