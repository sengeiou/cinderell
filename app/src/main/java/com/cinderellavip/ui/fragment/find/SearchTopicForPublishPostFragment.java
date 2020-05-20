package com.cinderellavip.ui.fragment.find;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.SearchTopicForPublishPostAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.find.HotTopicItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SearchTopicForPublishPostFragment extends BaseListFragment<HotTopicItem> {

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
        setEmptyView("暂无话题信息");

    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent();
            List<HotTopicItem> data = mAdapter.getData();
            intent.putExtra("data",data.get(position));
            mActivity.setResult(Activity.RESULT_OK,intent);
            mActivity.finish();

        });
    }

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("keyword", ""+keyword);
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<HotTopicItem>>>().send(ApiManager.getService().discuss_search_topic(hashMap),
                new Response<BaseResult<ListResult<HotTopicItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HotTopicItem>> result) {
                        setData(result.data.list);
                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


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
