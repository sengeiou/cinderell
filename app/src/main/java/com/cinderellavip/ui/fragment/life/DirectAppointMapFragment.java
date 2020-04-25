package com.cinderellavip.ui.fragment.life;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.MapItem;
import com.tozzais.baselibrary.ui.BaseFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class DirectAppointMapFragment extends BaseFragment implements AMap.OnMyLocationChangeListener
        , AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener
        , AMap.CancelableCallback, AMap.OnCameraChangeListener {



    @BindView(R.id.map)
    MapView mMapView;

    private int zoom = 15;


    //初始化地图控制器对象
    AMap aMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    @Override
    public int setLayout() {
        return R.layout.fragment_directappointment_map;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        }

    }

    @Override
    public void loadData() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);


        addMarker(new MapItem());



    }
//
//    private void getData() {
//        TreeMap<String, String> map = new TreeMap<>();
//        map.put("user_id", GlobalParam.getUserId());
//        map.put("sign", SignUtil.getMd5(map));
//        new RxHttp<BaseListResult<MapItem>>().send(ApiManager.getService().getMap(map),
//                new Response<BaseListResult<MapItem>>(isLoad, mActivity) {
//                    @Override
//                    public void onSuccess(BaseListResult<MapItem> result) {
//                        mapItemList = result.data;
//                        Log.e("mapItem",mapItemList.size()+"");
//                        addMarker(result.data);
//
//                    }
//                });
//
//    }

    public Map<Marker, MapItem> map = new HashMap<>();
    private List<MapItem> mapItemList;

    private void addMarker(List<MapItem> data) {
        for (MapItem mapItem : data) {
            if (mapItem == null || TextUtils.isEmpty(mapItem.lat) || TextUtils.isEmpty(mapItem.lng)) {
                continue;
            }
            addMarker(mapItem);
        }

    }

    private void addMarker(MapItem mapItem) {

        View view = View.inflate(mActivity.getApplicationContext(), R.layout.item_map, null);
        ImageView iv_image = view.findViewById(R.id.iv_image);
//        LatLng latLng = new LatLng(Double.parseDouble(mapItem.lat), Double.parseDouble(mapItem.lng));
        LatLng latLng = new LatLng(31.1995873500, 121.2669181800);
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng).draggable(false);



//        Glide.with(mActivity).load(HttpUrl.image_url+mapItem.logo).addListener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                iv_image.setImageDrawable(resource);
                Bitmap bitmap = getViewBitmap(view);
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                Marker marker = aMap.addMarker(markerOption);
                map.put(marker, mapItem);
                marker.showInfoWindow();
//                return false;
//            }
//        }).into(iv_image);



    }

    public Bitmap getViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED));
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
        view.buildDrawingCache();
        return Bitmap.createBitmap(view.getDrawingCache());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null)
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mMapView != null)
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mMapView != null)
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null)
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void initListener() {
        super.initListener();
        mUiSettings.setZoomControlsEnabled(false);
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(zoom);
        aMap.animateCamera(cameraUpdate, 100, null);
        aMap.setOnCameraChangeListener(this);
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {

    }
}
