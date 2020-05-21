package com.cinderellavip.ui.fragment.order;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.RefundAdapter;
import com.cinderellavip.bean.ListOrders;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class RefundFragment extends BaseListFragment<OrderBean> {




    public static RefundFragment newInstance(){
        RefundFragment cartFragment = new RefundFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new RefundAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无退款");



    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<ListOrders<OrderBean>>>().send(ApiManager.getService().refundOrderList(hashMap),
                new Response<BaseResult<ListOrders<OrderBean>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListOrders<OrderBean>> result) {
                        setData(result.data.orders);

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


}
