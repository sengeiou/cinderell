package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.cinderellavip.R;
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


public class SearchResultFragment extends BaseListFragment<HomeGoods> {


    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_search_result;
    }

    public String sort = "0",area = "0",type_child_ids = "";
    /**
     * 一级商品lieb
     * @param type  parent_id == 0 就是一级
     * @param type_id 一级分类ID
     * @return
     */
    private int third_category_id;
    public static SearchResultFragment newInstance(int third_category_id) {
        SearchResultFragment cartFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("third_category_id", third_category_id);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment cartFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        third_category_id = getArguments().getInt("third_category_id");
        keyword = getArguments().getString("keyword");

        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        setLayoutManager();



        setEmptyView("没有更多商品，换个关键字试试");

    }



    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("third_category_id", ""+third_category_id);
        hashMap.put("sort", "0");
        hashMap.put("sort_type", "0");
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getHomeMoreGoods(hashMap),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }
                });

    }
    public void setSortAndArea(String sort, String area){
        this.sort = sort;
        this.area = area;
        onRefresh();

    }
    public void setKeyword(String keyword){
        this.keyword = keyword;
        onRefresh();

    }

    public void setTypeIds(String type_child_ids){
        this.type_child_ids = type_child_ids;
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
