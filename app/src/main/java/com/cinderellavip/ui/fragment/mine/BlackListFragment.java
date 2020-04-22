package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.RefundAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class BlackListFragment extends BaseListFragment<OrderBean> {




    public static BlackListFragment newInstance(){
        BlackListFragment cartFragment = new BlackListFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new RefundAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无黑名单");



    }

    @Override
    public void loadData() {
        super.loadData();
        List<OrderBean> list = new ArrayList<>();
        list.add(new OrderBean(0));
        list.add(new OrderBean(1));
        list.add(new OrderBean(2));

        setData(list);

    }


}
