package com.cinderellavip.ui.fragment.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectCityAddressAdapter;
import com.cinderellavip.map.LocationUtil;
import com.cinderellavip.ui.activity.life.SelectCityActivity;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;

public class SelectLocationFragment extends BaseListFragment<PoiItem> implements PoiSearch.OnPoiSearchListener {


    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @Override
    public int setLayout() {
        return R.layout.fragment_select_location_life;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SelectCityAddressAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无地址");

    }

    @Override
    public void loadData() {
        super.loadData();
        location();

    }
    private void location(){
        LocationUtil util = new LocationUtil();
        util.start(mActivity,(aMapLocation, lat, lnt) -> {
            if (aMapLocation.getErrorCode() == 0){
                searchNearbyAddress(et_search.getText().toString(),
                        lat,lnt,tvAddress.getText().toString());
            }
            util.stop();
        });
    }
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;
    private PoiResult poiResult; // poi返回的结果
    private List<PoiItem> poiItems;// poi数据
    private  void searchNearbyAddress(String search,double lat,double lng,String city){
        query = new PoiSearch.Query(search, "", tvAddress.getText().toString());
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码
            poiSearch = new PoiSearch(mActivity, query);
            poiSearch.setOnPoiSearchListener(this);
            if (lat != 0 )
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lng), 5000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    //选中的item
    private PoiItem netCityBean;

    @Override
    public void initListener() {
//        super.initListener();
        swipeLayout.setEnabled(false);
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<PoiItem> data = mAdapter.getData();
            netCityBean = data.get(position);
            Intent intent = new Intent();
            intent.putExtra("netCityBean", netCityBean);
            mActivity.setResult(Activity.RESULT_OK, intent);
            mActivity.finish();

        }));
        et_search.setOnKeyListener((v, keyCode, event) -> {
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                KeyboardUtils.hideKeyboard(et_search);
                String keyword = et_search.getText().toString().trim();
                searchNearbyAddress(et_search.getText().toString(),
                        0,0,tvAddress.getText().toString());
            }
            return false;
        });
    }


    @OnClick(R.id.tv_address)
    public void onClick() {
        SelectCityActivity.launch(mActivity);
    }

    public void setAddress(String address) {
        tvAddress.setText(address);
        searchNearbyAddress(et_search.getText().toString(),
                0,0,tvAddress.getText().toString());
    }


    @Override
    public void onPoiSearched(PoiResult result, int rcode) {

        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    if (poiItems != null){
                        poiItems.clear();
                    }
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> cities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        setData(true,poiItems);
                    }else {
                        setData(true,poiItems);
                    }
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
