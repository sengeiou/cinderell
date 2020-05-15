package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.LogisticsAdapter;
import com.cinderellavip.bean.net.IntegralExchangeLogistics;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

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
        super.loadData();
        new RxHttp<BaseResult<IntegralExchangeLogistics>>().send(ApiManager.getService().getLogistics(getArguments().getString("post_no")),
                new Response<BaseResult<IntegralExchangeLogistics>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<IntegralExchangeLogistics> result) {
                        setData(result.data);

                    }
                });


    }

    private void setData(IntegralExchangeLogistics data) {
        tv_name.setText(data.company);
        tv_number.setText(data.number);
        setData(data.list);

    }

    @Override
    public void initListener() {
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);

    }
}
