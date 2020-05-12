package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;
import android.text.TextUtils;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.adapter.recycleview.SearchGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.SearchListActivity;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;


public class BrandSearchResultFragment extends BaseListFragment<HomeGoods> {


    public static BrandSearchResultFragment newInstance(String id,int type) {
        BrandSearchResultFragment cartFragment = new BrandSearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    private int type;
    private String id;
    private String keyword = "";
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getString("id");
        type = getArguments().getInt("type");
        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        setLayoutManager();


        setEmptyView("暂无搜索结果");

    }



    @Override
    public void loadData() {

        TreeMap<String, String> hashMap = new TreeMap<>();
        if (type == SearchListActivity.BRAND){
            hashMap.put("brand_id", ""+id);
        }else if (type == SearchListActivity.SHOP){
            hashMap.put("store_id", ""+id);
        }
        hashMap.put("keyword", ""+keyword);
        hashMap.put("sort", "0");
        hashMap.put("sort_type", "0");
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getBrandGoods(hashMap),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
        swipeLayout.setRefreshing(true);
        onRefresh();

    }

    private  GirdSpace girdSpace;
    private boolean isGrid = true;
    private void setLayoutManager(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }







}
