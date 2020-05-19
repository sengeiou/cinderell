package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.bean.net.find.ListDiscussesResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.GirdSpaceStag;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class FindAttentionFragment extends BaseListFragment<FindItem> {


    public static FindAttentionFragment newInstance() {
        FindAttentionFragment cartFragment = new FindAttentionFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static FindAttentionFragment newInstance(String keyword) {
        FindAttentionFragment cartFragment = new FindAttentionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        keyword = getArguments().getString("keyword");
        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10),2,0,true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("没有关注信息");

    }



    @Override
    public void loadData() {
        super.loadData();
        new RxHttp<BaseResult<ListDiscussesResult>>().send(ApiManager.getService().getDiscussCollects(),
                new Response<BaseResult<ListDiscussesResult>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListDiscussesResult> result) {
                            setData(result.data.discusses);
                    }
                });

    }


    @Override
    public void initListener() {
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
    }
}
