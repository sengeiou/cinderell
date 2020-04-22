package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ServiceListAdapter;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class ServiceListFragment extends BaseListFragment<String> {


    @Override
    public int setLayout() {
        return R.layout.fragment_service_list;
    }


    public static ServiceListFragment newInstance() {
        ServiceListFragment cartFragment = new ServiceListFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new ServiceListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无数据");

        View view = View.inflate(mActivity, R.layout.header_service_list, null);
        mAdapter.addHeaderView(view);


    }

    @Override
    public void loadData() {
        super.loadData();


        setData(DataUtil.getData(2));

    }


    @OnClick(R.id.tv_service)
    public void onClick() {
    }
}
