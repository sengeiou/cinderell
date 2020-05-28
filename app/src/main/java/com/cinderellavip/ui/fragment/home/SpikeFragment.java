package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SpikeListAdapter;
import com.cinderellavip.bean.ListOrders;
import com.cinderellavip.bean.eventbus.OrderComment;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.LinearSpace;
import com.cinderellavip.weight.LinearSpace1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SpikeFragment extends BaseListFragment<String> {


    private int type;
    public static SpikeFragment newInstance(int type){
        SpikeFragment cartFragment = new SpikeFragment();
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
        LinearSpace1 girdSpace = new LinearSpace1(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);

        mAdapter = new SpikeListAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{
//
//        });

        setEmptyView("暂无秒杀~");



    }

    @Override
    public void loadData() {
        super.loadData();
//        getData();
        setData(DataUtil.getData(type));


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
//                        setData(result.data.orders);
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

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof ReceiveOrder||o instanceof OrderComment){
            onRefresh();
        }
    }
}
