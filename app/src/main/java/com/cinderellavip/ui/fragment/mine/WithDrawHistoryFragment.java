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
import com.cinderellavip.ui.activity.mine.WithDrawHistoryActivity;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class WithDrawHistoryFragment extends BaseListFragment<WithDrawHistoryItem>  {

    private TextView tv_balance;
    private TextView tv_explain;


    public static WithDrawHistoryFragment newInstance(int type){
        WithDrawHistoryFragment cartFragment = new WithDrawHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    private int type;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new WithDrawHistoryAdapter(type);
        mRecyclerView.setAdapter(mAdapter);

        View header_view = View.inflate(mActivity, R.layout.header_withdraw_history, null);
        tv_balance = header_view.findViewById(R.id.tv_balance);
        tv_explain = header_view.findViewById(R.id.tv_explain);
        mAdapter.addHeaderView(header_view);
    }

    @Override
    public void loadData() {
        super.loadData();
        if(type == WithDrawHistoryActivity.BALANCE){
            getBalanceHistory();
        }if(type == WithDrawHistoryActivity.SCORE){
            getScoreHistory();
        }

    }

    private void getBalanceHistory(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");
        new RxHttp<BaseResult<WithDrawHistoryResult>>().send(ApiManager.getService().withDrawHistory(hashMap),
                new Response<BaseResult<WithDrawHistoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<WithDrawHistoryResult> result) {
                        WithDrawHistoryResult data = result.data;
                        if (page == DEFAULT_PAGE) {
                            tv_explain.setText("已提现金额");
                            tv_balance.setText("￥"+data.withdraw_sum);
                        }
                        setData(data.list);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    private void getScoreHistory(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");
        new RxHttp<BaseResult<WithDrawHistoryResult>>().send(ApiManager.getService().scoreWithDrawHistory(hashMap),
                new Response<BaseResult<WithDrawHistoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<WithDrawHistoryResult> result) {
                        WithDrawHistoryResult data = result.data;
                        if (page == DEFAULT_PAGE) {
                            tv_explain.setText("已提现积分");
                            tv_balance.setText(""+data.withdraw_sum);
                        }
                        setData(data.list);
                    }
                    @Override
                    public void onError(Throwable e) {
                        onErrorResult(e);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
    }

}
