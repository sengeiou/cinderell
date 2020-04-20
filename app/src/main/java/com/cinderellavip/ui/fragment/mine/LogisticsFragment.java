package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.LogisticsAdapter;
import com.cinderellavip.bean.net.IntegralExchangeLogistics;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class LogisticsFragment extends BaseListFragment<IntegralExchangeLogistics.ListBean> {



    public static LogisticsFragment newInstance(String post_no){
        LogisticsFragment cartFragment = new LogisticsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("post_no",post_no);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new LogisticsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无物流详情");

        View view = View.inflate(mActivity, R.layout.item_logistics_header, null);
        tv_name = view.findViewById(R.id.tv_name);
        tv_number = view.findViewById(R.id.tv_number);
        mAdapter.addHeaderView(view);



    }

    private TextView tv_name;
    private TextView tv_number;



    @Override
    public void loadData() {
        List<IntegralExchangeLogistics.ListBean> list = new ArrayList();
        list.add(new IntegralExchangeLogistics.ListBean());
        list.add(new IntegralExchangeLogistics.ListBean());
        list.add(new IntegralExchangeLogistics.ListBean());
        list.add(new IntegralExchangeLogistics.ListBean());
        list.add(new IntegralExchangeLogistics.ListBean());
        setData(list);


    }

    private void setData(IntegralExchangeLogistics data) {
        tv_name.setText(data.expName);
        tv_number.setText(data.number);
        setData(data.list);

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

    }
}
