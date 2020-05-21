package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

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


public class GoodsListFragment extends BaseListFragment<HomeGoods> {




    public String sort = "0",sort_type = "0",type_child_ids = "";
    /**
     * 一级商品lieb
     * @param type  parent_id == 0 就是一级
     * @param type_id 一级分类ID
     * @return
     */
    private int third_category_id;
    public static GoodsListFragment newInstance(int third_category_id) {
        GoodsListFragment cartFragment = new GoodsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("third_category_id", third_category_id);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        third_category_id = getArguments().getInt("third_category_id");

        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        setLayoutManager();

        setEmptyView("暂无数据");

    }



    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("third_category_id", ""+third_category_id);
        hashMap.put("sort", sort);
        hashMap.put("sort_type", sort_type);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getHomeMoreGoods(hashMap),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad,getContext()) {
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

    private  GirdSpace girdSpace;
    private boolean isGrid = true;
    private void setLayoutManager(){
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }







}
