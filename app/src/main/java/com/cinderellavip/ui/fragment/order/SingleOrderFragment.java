package com.cinderellavip.ui.fragment.order;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SingleOrderAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SingleOrderFragment extends BaseListFragment<Integer> {


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

        setEmptyView(R.mipmap.empty_view,"您还没有相关订单哦~","去逛逛", view->{

        });



    }

    @Override
    public void loadData() {
        if (swipeLayout != null)
        swipeLayout.setRefreshing(false);
        List<Integer> list = new ArrayList<>();
        if (type == 3)
        list.add(4);
        list.add(type);
        list.add(type);
        list.add(type);
        list.add(type);
        list.add(type);
       setData(true, list);


    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }
}
