package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineBalanceAdpter;
import com.cinderellavip.adapter.recycleview.WithDrawHistoryAdapter;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;


public class WithDrawHistoryFragment extends BaseListFragment<String>  {

    private TextView tv_balance;



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.gray_line));
        mAdapter = new WithDrawHistoryAdapter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView("暂无余额");

        View header_view = View.inflate(mActivity, R.layout.header_withdraw_history, null);
        tv_balance = header_view.findViewById(R.id.tv_balance);
        mAdapter.addHeaderView(header_view);
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
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
    }

}
