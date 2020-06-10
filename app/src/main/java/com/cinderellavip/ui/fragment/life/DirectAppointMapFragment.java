package com.cinderellavip.ui.fragment.life;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cinderellavip.R;
import com.cinderellavip.bean.direct.DirectMapPersonItem;
import com.cinderellavip.global.CinderellaApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianDetailActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class DirectAppointMapFragment extends BaseFragment implements AMap.OnMyLocationChangeListener
        , AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener
        , AMap.CancelableCallback, AMap.OnCameraChangeListener {


    private int service;
    public static DirectAppointMapFragment newInstance(int service){
        DirectAppointMapFragment cartFragment = new DirectAppointMapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("service",service);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    public void setId(int id) {
        if (isLoad){
            this.service = id;
            getData();
        }
    }

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
        service = getArguments().getInt("service");
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

        getData();


    }
    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("service", ""+service);
        hashMap.put("city", ""+ CinderellaApplication.name);
        hashMap.put("longitude", ""+ CinderellaApplication.longitude);
        hashMap.put("latitude", ""+ CinderellaApplication.latitude);
        new RxHttp<BaseListResult<DirectMapPersonItem>>().send(ApiManager.getService().getDirectMapPerson(hashMap),
                new Response<BaseListResult<DirectMapPersonItem>>(getContext(),Response.BOTH) {
                    @Override
                    public void onSuccess(BaseListResult<DirectMapPersonItem> result) {
                        addMarker(result.data);
                    }
                });
    }


    public Map<Marker, DirectMapPersonItem> map = new HashMap<>();

    private void addMarker(List<DirectMapPersonItem> data) {
        for (DirectMapPersonItem mapItem : data) {
            if (mapItem == null || TextUtils.isEmpty(mapItem.latitude) || TextUtils.isEmpty(mapItem.longitude)) {
                continue;
            }
            addMarker(mapItem);
        }

    }

    private void addMarker(DirectMapPersonItem mapItem) {
        LogUtil.e(mapItem.toString());
        View view = View.inflate(mActivity.getApplicationContext(), R.layout.item_map, null);
        ImageView iv_image = view.findViewById(R.id.iv_image);
        LatLng latLng = new LatLng(Double.parseDouble(mapItem.latitude), Double.parseDouble(mapItem.longitude));
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng).draggable(false);
        Glide.with(mActivity.getApplicationContext())
                .load(mapItem.avatar).override(100,100).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                LogUtil.e("加载失败"+e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                LogUtil.e("加载成功");
                iv_image.setImageDrawable(resource);
                Bitmap bitmap = getViewBitmap(view);
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                Marker marker = aMap.addMarker(markerOption);
                map.put(marker, mapItem);
                marker.showInfoWindow();
                return false;
            }
        }).into(iv_image);
    }

    public Bitmap getViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
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

//        aMap.setLocationSource(this);// 设置定位监听
//        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
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
        DirectMapPersonItem directMapPersonItem = map.get(marker);
        DirectAppointmentTechnicianDetailActivity.launch(getContext(), directMapPersonItem.id);
        return false;
    }

    private double latitude,longitude;
    @Override
    public void onMyLocationChange(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
//        Log.e("latitude", location.getLatitude() + "");
//        Log.e("longitude", location.getLongitude() + "");
    }

    @OnClick(R.id.iv_location)
    public void onClick() {
        LatLng latLng = new LatLng(latitude,longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.zoomTo(zoom);
        aMap.animateCamera(cameraUpdate, zoom, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            }
            @Override
            public void onCancel() {

            }
        });
    }
}
