package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.adapter.recycleview.CollectShopAdapter;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * 小金推荐-消费积分
 */
public class CollectShopFragment extends BaseListFragment<String> {




    @Override
    public void loadData() {

        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(10));
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
        mAdapter = new CollectShopAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }


}