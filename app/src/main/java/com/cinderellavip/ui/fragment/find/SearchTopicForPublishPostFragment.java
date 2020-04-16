package com.cinderellavip.ui.fragment.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.SearchTopicForPublishPostAdapter;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SearchTopicForPublishPostFragment extends BaseListFragment<String> {








    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static SearchTopicForPublishPostFragment newInstance(String keyword) {
        SearchTopicForPublishPostFragment cartFragment = new SearchTopicForPublishPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        keyword = getArguments().getString("keyword");

        setLayoutManager();



        setEmptyView("没有更多商品，换个关键字试试");

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            mActivity.setResult(Activity.RESULT_OK,intent);
            mActivity.finish();

        });
    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(8));
        }, 100);


    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
        onRefresh();

    }


    private void setLayoutManager(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SearchTopicForPublishPostAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }







}
