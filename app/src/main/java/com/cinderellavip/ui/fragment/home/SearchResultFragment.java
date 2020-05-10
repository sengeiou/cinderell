package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SearchGoodsAdapter;
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

    public String sort = "0",sort_type = "0",type_child_ids = "";



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
        keyword = getArguments().getString("keyword");

        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,0,true);
        setLayoutManager();



        setEmptyView("暂无搜索结果");

    }



    @Override
    public void loadData() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("keyword", ""+keyword);
        hashMap.put("sort", sort);
        hashMap.put("sort_type", sort_type);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getSearchGoods(hashMap),
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
    public void setSortAndArea(String sort, String sort_type){
        this.sort = sort;
        this.sort_type = sort_type;
        swipeLayout.setRefreshing(true);
        onRefresh();

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
        mAdapter = new SearchGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }







}
