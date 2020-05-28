package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.SpikeListAdapter;
import com.cinderellavip.bean.eventbus.OrderComment;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.net.home.HomeSpikeItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.LinearSpace1;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SpikeFragment extends BaseListFragment<HomeSpikeItem> {


    private String time;
    public static SpikeFragment newInstance(String time){
        SpikeFragment cartFragment = new SpikeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("time",time);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        time = getArguments().getString("time");

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
        getData();


    }

    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("time", ""+time);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HomeSpikeItem>>>().send(ApiManager.getService().spikeList(hashMap),
                new Response<BaseResult<ListResult<HomeSpikeItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeSpikeItem>> result) {
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
