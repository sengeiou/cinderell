package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;

import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.GridLayoutManager;


public class ShopDetailFragment extends BaseListFragment<HomeGoods> {



    //店铺id
    public static ShopDetailFragment newInstance(String store_id) {
        ShopDetailFragment cartFragment = new ShopDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", store_id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    public static ShopDetailFragment newInstance(String store_id,String category_id) {
        ShopDetailFragment cartFragment = new ShopDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", store_id);
        bundle.putString("category_id", category_id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    public String sort = "0", sort_type = "0";
    private  String id;
    private  String category_id;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getString("id");
        category_id = getArguments().getString("category_id");
        if (TextUtils.isEmpty(category_id)){
            category_id = "";
        }

        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        GirdSpace girdSpace =  new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", "" + id);
        hashMap.put("sort", sort);
        hashMap.put("sort_type", sort_type);
        hashMap.put("limit", "" + PageSize);
        hashMap.put("page", "" + page);
        hashMap.put("category_id", "" + category_id);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getBrandGoods(hashMap),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad, getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }
    public void setSortAndArea(String sort, String sort_type){
        this.sort = sort;
        this.sort_type = sort_type;
        swipeLayout.setRefreshing(true);
        onRefresh();

    }

}
