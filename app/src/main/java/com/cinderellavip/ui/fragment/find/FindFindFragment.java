package com.cinderellavip.ui.fragment.find;

import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.adapter.recycleview.FindHotTopicAdapter;
import com.cinderellavip.bean.eventbus.UpdateFind;
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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class FindFindFragment extends BaseListFragment<FindItem> {


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

        setEmptyView("暂无数据~");

    }

    private RecyclerView rv_hot_topic;
    private FindHotTopicAdapter findHotTopicAdapter;
    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_find_find, null);
        RecyclerView rv_hot_topic = headerView.findViewById(R.id.rv_hot_topic);
        rv_hot_topic.setLayoutManager(new GridLayoutManager(mActivity,2));
        findHotTopicAdapter = new FindHotTopicAdapter();
        rv_hot_topic.setAdapter(findHotTopicAdapter);
        mAdapter.addHeaderView(headerView);
        findHotTopicAdapter.setOnItemClickListener((adapter1, view, position) -> {
//            TopicDetailActivity.launch(mActivity);
        });



    }



    @Override
    public void loadData() {
        super.loadData();
//
//        new Handler().postDelayed(() -> {
//            setData(DataUtil.getData(4));
//        }, 100);
        getData();


    }

    private void  getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListDiscussesResult>>().send(ApiManager.getService().getFindList(hashMap),
                new Response<BaseResult<ListDiscussesResult>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListDiscussesResult> result) {
                        ListDiscussesResult discussesResult = result.data;
                        if (page == DEFAULT_PAGE){
                            findHotTopicAdapter.setNewData(discussesResult.hot_topics);
                        }
                        setData(discussesResult.discusses);
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
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            PostDetailActivity.launch(mActivity);
        });

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateFind){
            onRefresh();
        }
    }
}
