package com.cinderellavip.ui.fragment.order;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.RefundAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
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
        List<OrderBean> list = new ArrayList<>();
        list.add(new OrderBean(0));
        list.add(new OrderBean(1));
        list.add(new OrderBean(2));

        setData(list);

    }


}
