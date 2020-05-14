package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SmallVaultLeaderBoardAdapter;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.bean.net.mine.IntegralResult;
import com.cinderellavip.bean.net.mine.RankItem;
import com.cinderellavip.bean.net.mine.RankResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.DataUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class LeaderBoardFragment extends BaseListFragment<RankItem> {

    @Override
    public void loadData() {

        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("month", "");
        hashMap.put("page", page + "");
        hashMap.put("limit", PageSize + "");
        new RxHttp<BaseResult<RankResult>>().send(ApiManager.getService().ranking(hashMap),
                new Response<BaseResult<RankResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<RankResult> result) {
                        for (RankItem rankItem:result.data.teams){
                            rankItem.text1 = result.data.title;
                            rankItem.text2 = result.data.date;
                        }
                        setData(result.data.teams);
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
        mAdapter = new SmallVaultLeaderBoardAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }



}
