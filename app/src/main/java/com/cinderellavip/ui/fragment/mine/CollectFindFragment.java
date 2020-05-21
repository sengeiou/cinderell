package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;

import com.cinderellavip.adapter.recycleview.FindAdapter;
import com.cinderellavip.bean.net.find.FindItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.weight.GirdSpaceStag;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 *
 */
public class CollectFindFragment extends BaseListFragment<FindItem> {



    public static CollectFindFragment newInstance() {
        CollectFindFragment cartFragment = new CollectFindFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;
    }






    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        GirdSpaceStag girdSpace = new GirdSpaceStag(DpUtil.dip2px(mActivity, 10),2,0,true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new FindAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无收藏帖子");

    }



    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", "3");
        hashMap.put("limit", ""+PageSize);
        hashMap.put("page", ""+page);
        new RxHttp<BaseResult<ListResult<FindItem>>>().send(ApiManager.getService().mineCollect(hashMap),
                new Response<BaseResult<ListResult<FindItem>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<FindItem>> result) {
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


}
