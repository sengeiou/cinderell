package com.cinderellavip.ui.fragment.order;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SingleOrderAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.bean.net.life.LongOrderItem;
import com.cinderellavip.bean.net.life.ShortOrderItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SingleOrderFragment extends BaseListFragment<ShortOrderItem> {


    private int type;

    public static SingleOrderFragment newInstance(int type){
        SingleOrderFragment cartFragment = new SingleOrderFragment();
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
        mAdapter = new SingleOrderAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无单次服务订单");

    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("pay", ""+(type-1));
        hashMap.put("count", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListData<ShortOrderItem>>>().send(ApiManager.getService().shortOrderList(hashMap),
                new Response<BaseResult<ListData<ShortOrderItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<ShortOrderItem>> result) {
                        setData(result.data.data);
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
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateShortServiceOrder){
            onRefresh();
        }
    }
}
