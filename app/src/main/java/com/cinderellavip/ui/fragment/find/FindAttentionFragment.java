package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.GirdSpaceStag;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class FindAttentionFragment extends BaseListFragment<String> {


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

        setEmptyView("没有更多商品，换个关键字试试");

    }



    @Override
    public void loadData() {
        super.loadData();
//
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(4));
        }, 100);


    }








}
