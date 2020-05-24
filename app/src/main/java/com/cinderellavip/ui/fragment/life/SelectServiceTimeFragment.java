package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectServiceTimeAdapter;
import com.cinderellavip.bean.net.life.ShortTime;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;


public class SelectServiceTimeFragment extends BaseListFragment<ShortTime> {



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


    }


    @Override
    public void initListener() {
        super.initListener();

    }

    public void setDate(List<ShortTime> time) {
        setData(true,time);
    }
}
