package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SearchShopAdapter;
import com.cinderellavip.bean.ListOrders;
import com.cinderellavip.bean.eventbus.OrderComment;
import com.cinderellavip.bean.eventbus.OrderPaySuccess;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.LinearSpace1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;


public class SearchShopFragment extends BaseListFragment<String> {



    private String keyword;
    public static SearchShopFragment newInstance(String keyword) {
        SearchShopFragment fragment = new SearchShopFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        keyword = getArguments().getString("keyword");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        LinearSpace1 girdSpace = new LinearSpace1(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new SearchShopAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("没有相关店铺哦~");



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
//        TreeMap<String, String> hashMap = new TreeMap<>();
//        hashMap.put("status", ""+type);
//        hashMap.put("limit", ""+PageSize);
//        hashMap.put("page", ""+page);
//        new RxHttp<BaseResult<ListOrders<OrderBean>>>().send(ApiManager.getService().getOrderList(hashMap),
//                new Response<BaseResult<ListOrders<OrderBean>>>(isLoad,getContext()) {
//                    @Override
//                    public void onSuccess(BaseResult<ListOrders<OrderBean>> result) {
                        setData(DataUtil.getData(3));
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        onErrorResult(e);
//                    }
//                    @Override
//                    public void onErrorShow(String s) {
//                        showError(s);
//                    }
//                });
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        swipeLayout.setRefreshing(true);
        onRefresh();

    }


}
