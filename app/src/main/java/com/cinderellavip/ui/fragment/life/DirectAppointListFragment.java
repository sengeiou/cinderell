package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.DirectListAdapter;
import com.cinderellavip.ui.activity.life.DirectAppointmentTechnicianCommentActivity;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;

public class DirectAppointListFragment extends BaseListFragment<String> {






    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new DirectListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无数据");

    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            setData(DataUtil.getData(8));
        },500);



    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {


        }));
    }





}
