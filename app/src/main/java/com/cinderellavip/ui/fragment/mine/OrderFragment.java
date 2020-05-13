package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.bean.ListOrders;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class OrderFragment extends BaseListFragment<OrderBean> {


    public static final int ALL = 0,UNPAY = 1,UNSEND = 2,UNRECEIVE = 3,FINISH = 4;
    private int type;

    public static OrderFragment newInstance(int type){
        OrderFragment cartFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new OrderAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{

        });



    }

    @Override
    public void loadData() {
        super.loadData();
        getData();


    }

    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("status", ""+type);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListOrders<OrderBean>>>().send(ApiManager.getService().getOrderList(hashMap),
                new Response<BaseResult<ListOrders<OrderBean>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListOrders<OrderBean>> result) {
                        setData(result.data.orders);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof ReceiveOrder){
            onRefresh();
        }
    }
}
