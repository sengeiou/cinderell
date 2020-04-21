package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SmallVaultLeaderBoardAdapter;
import com.cinderellavip.adapter.recycleview.XiaohuiRecommentAdapter;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.util.DataUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * 小金推荐
 */
public class LeaderBoardFragment extends BaseListFragment<String> {

    @Override
    public void loadData() {

        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(18));
        }, 100);
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
