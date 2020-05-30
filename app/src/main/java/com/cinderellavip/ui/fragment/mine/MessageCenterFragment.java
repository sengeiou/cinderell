package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.MessageCenterAdapter;
import com.cinderellavip.bean.net.mine.MessageItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;


public class MessageCenterFragment extends BaseListFragment<MessageItem> {




    public static MessageCenterFragment newInstance(){
        MessageCenterFragment cartFragment = new MessageCenterFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new MessageCenterAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无消息");



    }

    @Override
    public void loadData() {
        super.loadData();
        new RxHttp<BaseResult<ListResult<MessageItem>>>().send(ApiManager.getService().messageCenter(),
                new Response<BaseResult<ListResult<MessageItem>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<MessageItem>> result) {
                        List<MessageItem> list = result.data.list;
                        LogUtil.e("list"+list.size());
                        setData(list);
                    }
                });

    }
    @Override
    public void onResume() {
        onRefresh();
        super.onResume();
    }


}
