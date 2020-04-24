package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.OrderAdapter;
import com.cinderellavip.adapter.recycleview.SelectServiceTimeAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.bean.local.TimeBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;


public class SelectServiceTimeFragment extends BaseListFragment<TimeBean> {



    public static SelectServiceTimeFragment newInstance(String name){
        SelectServiceTimeFragment cartFragment = new SelectServiceTimeFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    @Override
    public int setLayout() {
        return R.layout.fragment_recycleview_select_service_time;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,4));
        mAdapter = new SelectServiceTimeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无时间可以选择");



    }

    @Override
    public void loadData() {
        List<TimeBean> list = new ArrayList<>();
        list.add(new TimeBean(0,"08:00"));
        list.add(new TimeBean(0,"08:30"));
        list.add(new TimeBean(0,"09:00"));
        list.add(new TimeBean(0,"09:30"));
        list.add(new TimeBean(0,"10:00"));
        list.add(new TimeBean(0,"10:30"));
        list.add(new TimeBean(0,"11:00"));
        list.add(new TimeBean(0,"11:30"));
        list.add(new TimeBean(0,"12:00"));
        list.add(new TimeBean(0,"12:30"));
        list.add(new TimeBean(1,"13:00"));
        list.add(new TimeBean(1,"13:30"));
        list.add(new TimeBean(1,"14:00"));
        list.add(new TimeBean(1,"14:30"));
        list.add(new TimeBean(1,"15:00"));
        list.add(new TimeBean(1,"15:30"));
        list.add(new TimeBean(1,"16:00"));
        list.add(new TimeBean(1,"16:30"));
        list.add(new TimeBean(1,"17:00"));
        list.add(new TimeBean(1,"17:30"));
        list.add(new TimeBean(1,"18:00"));

       setData(true, list);


    }

    @Override
    public void initListener() {
        super.initListener();

    }
}
