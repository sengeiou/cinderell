package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.BlackListAdapter;
import com.cinderellavip.bean.net.mine.BlacklistItem;
import com.cinderellavip.bean.net.mine.BlacklistResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class BlackListFragment extends BaseListFragment<BlacklistItem> {




    public static BlackListFragment newInstance(){
        BlackListFragment cartFragment = new BlackListFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new BlackListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无黑名单");



    }

    @Override
    public void loadData() {
        super.loadData();super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<BlacklistResult>>().send(ApiManager.getService().getBlackList(hashMap),
                new Response<BaseResult<BlacklistResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<BlacklistResult> result) {

                        setData(result.data.users);
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


}
