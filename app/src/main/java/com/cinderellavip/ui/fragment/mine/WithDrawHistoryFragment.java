package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineBalanceAdpter;
import com.cinderellavip.adapter.recycleview.WithDrawHistoryAdapter;
import com.cinderellavip.bean.net.mine.MineBalanceResult;
import com.cinderellavip.bean.net.mine.WithDrawHistoryItem;
import com.cinderellavip.bean.net.mine.WithDrawHistoryResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class WithDrawHistoryFragment extends BaseListFragment<WithDrawHistoryItem>  {

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
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");

        new RxHttp<BaseResult<WithDrawHistoryResult>>().send(ApiManager.getService().withDrawHistory(hashMap),
                new Response<BaseResult<WithDrawHistoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<WithDrawHistoryResult> result) {
                        WithDrawHistoryResult data = result.data;
                        if (page == DEFAULT_PAGE) {
                            tv_balance.setText("￥"+data.withdraw_sum);
                        }
                        setData(data.list);
                    }
                });



    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
    }

}
