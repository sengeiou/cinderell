package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.MessageMineAssetAdapter;
import com.cinderellavip.adapter.recycleview.RefundAdapter;
import com.cinderellavip.bean.local.OrderBean;
import com.cinderellavip.bean.net.mine.BlacklistResult;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MessageMineAssetFragment extends BaseListFragment<MessageItem> {




    public static MessageMineAssetFragment newInstance(int type){
        MessageMineAssetFragment cartFragment = new MessageMineAssetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MessageMineAssetAdapter(type);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无消息");



    }

    private int type;
    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", type + "");
        hashMap.put("page", page + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<ListResult<MessageItem>>>().send(ApiManager.getService().messageList(hashMap),
                new Response<BaseResult<ListResult<MessageItem>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<MessageItem>> result) {

                        setData(result.data.list);
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
    }
}
