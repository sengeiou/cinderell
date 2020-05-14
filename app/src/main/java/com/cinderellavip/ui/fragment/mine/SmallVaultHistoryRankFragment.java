package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.SmallVaultHistoryRankAdapter;
import com.cinderellavip.adapter.recycleview.SmallVaultInterestIntegralAdapter;
import com.cinderellavip.bean.net.mine.RankItem;
import com.cinderellavip.bean.net.mine.RankMonthItem;
import com.cinderellavip.bean.net.mine.RankResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * 小金推荐-推荐积分
 */
public class SmallVaultHistoryRankFragment extends BaseListFragment<RankMonthItem> {




    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", page + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<ListResult<RankMonthItem>>>().send(ApiManager.getService().rankingMonth(hashMap),
                new Response<BaseResult<ListResult<RankMonthItem>>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<RankMonthItem>> result) {

                        setData(result.data.list);
                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(true);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);



    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SmallVaultHistoryRankAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }


}
