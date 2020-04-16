package com.cinderellavip.ui.fragment.find;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleAdapter;
import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.adapter.recycleview.FindHotTopicAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.ui.activity.find.PostDetailActivity;
import com.cinderellavip.ui.activity.find.TopicDetailActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.GirdSpaceStag;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class FindFindFragment extends BaseListFragment<String> {


    public static FindFindFragment newInstance() {
        FindFindFragment cartFragment = new FindFindFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;
    }


    @Override
    public int setLayout() {
        return com.tozzais.baselibrary.R.layout.base_fragment_recycleview_rightmargin;
    }

    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static FindFindFragment newInstance(String keyword) {
        FindFindFragment cartFragment = new FindFindFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        cartFragment.setArguments(bundle);
        return cartFragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        keyword = getArguments().getString("keyword");
        /**
         * 使用StaggeredGridLayoutManager  之后不能使用GirdSpace
         * 因为第一项和最后一项 不是固定的
         */
        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10),2,1,false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);


        initHeadView();

    }

    private RecyclerView rv_hot_topic;
    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_find_find, null);
        RecyclerView rv_hot_topic = headerView.findViewById(R.id.rv_hot_topic);
        rv_hot_topic.setLayoutManager(new GridLayoutManager(mActivity,2));
        FindHotTopicAdapter adapter = new FindHotTopicAdapter();
        rv_hot_topic.setAdapter(adapter);
        adapter.setNewData(DataUtil.getData(6));
        mAdapter.addHeaderView(headerView);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            TopicDetailActivity.launch(mActivity);
        });



    }



    @Override
    public void loadData() {
        super.loadData();
//
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(4));
        }, 100);


    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PostDetailActivity.launch(mActivity);
        });

    }
}
