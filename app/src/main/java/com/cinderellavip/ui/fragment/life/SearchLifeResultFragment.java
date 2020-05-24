package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.ServiceListProjectAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.net.life.LiftHomeServiceItem;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.LinearSpace;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


public class SearchLifeResultFragment extends BaseListFragment<LiftHomeServiceItem> {


    /**
     *
     * @param keyword 所搜页面进入
     * @return
     */
    private String keyword;
    public static SearchLifeResultFragment newInstance(String keyword) {
        SearchLifeResultFragment fragment = new SearchLifeResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword", keyword);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        keyword = getArguments().getString("keyword");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearSpace girdSpace = new LinearSpace(DpUtil.dip2px(mActivity, 12));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new ServiceListProjectAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无搜索结果");





    }



    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("keyWord", ""+keyword);
        hashMap.put("city", CinderellApplication.name);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListData<LiftHomeServiceItem>>>().send(ApiManager.getService().getLifeSearchResult(hashMap),
                new Response<BaseResult<ListData<LiftHomeServiceItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<LiftHomeServiceItem>> result) {
                        setData(result.data.data);
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

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        onRefresh();
    }

}
