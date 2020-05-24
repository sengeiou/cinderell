package com.cinderellavip.ui.fragment.order;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.LongServiceOrderAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.eventbus.UpdateLongServiceOrder;
import com.cinderellavip.bean.net.life.LongOrderItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class LongServiceOrderFragment extends BaseListFragment<LongOrderItem> {


    private int type;

    public static LongServiceOrderFragment newInstance(int type){
        LongServiceOrderFragment cartFragment = new LongServiceOrderFragment();
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
        mAdapter = new LongServiceOrderAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无长期服务订单");



    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", ""+type);
        hashMap.put("count", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListData<LongOrderItem>>>().send(ApiManager.getService().longOrderList(hashMap),
                new Response<BaseResult<ListData<LongOrderItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<LongOrderItem>> result) {
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
        if (o instanceof UpdateLongServiceOrder){
            onRefresh();
        }
    }
}
