package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineBalanceAdpter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.net.mine.MineBalanceItem;
import com.cinderellavip.bean.net.mine.MineBalanceResult;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.WithDrawActivity;
import com.cinderellavip.ui.activity.mine.WithDrawHistoryActivity;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MineBalanceFragment extends BaseListFragment<MineBalanceItem> implements View.OnClickListener {

    private TextView tv_balance;
    private TextView tv_withdraw_record;
    private TextView tv_top_up;



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.gray_line));
        mAdapter = new MineBalanceAdpter();
        mRecyclerView.setAdapter(mAdapter);

//        setEmptyView("暂无余额");

        View header_view = View.inflate(mActivity, R.layout.header_balance, null);
        tv_balance = header_view.findViewById(R.id.tv_balance);
        tv_withdraw_record = header_view.findViewById(R.id.tv_withdraw_record);
        tv_top_up = header_view.findViewById(R.id.tv_top_up);
        tv_top_up.setOnClickListener(this);
        tv_withdraw_record.setOnClickListener(this);
        mAdapter.addHeaderView(header_view);
    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");

        new RxHttp<BaseResult<MineBalanceResult>>().send(ApiManager.getService().mineBalance(hashMap),
                new Response<BaseResult<MineBalanceResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineBalanceResult> result) {
                        MineBalanceResult data = result.data;
                        tv_balance.setText(data.balance);
                        setData(data.list);
                    }
                });



    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_withdraw_record:
                WithDrawHistoryActivity.launch(mActivity);
                break;
            case R.id.tv_top_up:
                WithDrawActivity.launch(mActivity);
                break;
        }
    }
}
