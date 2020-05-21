package com.cinderellavip.ui.fragment.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CardSaleAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CardSaleFragment extends BaseListFragment<HomeGoods> {



    public static CardSaleFragment newInstance() {
        CardSaleFragment cartFragment = new CardSaleFragment();
        Bundle bundle = new Bundle();
        cartFragment.setArguments(bundle);
        return cartFragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10),2,1,false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);


        setEmptyView("暂无商品信息");

        initHeadView();


    }

    private RecyclerView rv_banner;
    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_cardsale, null);
        RecyclerView rv_banner = headerView.findViewById(R.id.rv_banner);
        rv_banner.setLayoutManager(new LinearLayoutManager(mActivity));
        CardSaleAdapter cardSaleAdapter = new CardSaleAdapter();
        rv_banner.setAdapter(cardSaleAdapter);
        cardSaleAdapter.setNewData(DataUtil.getData(2));
        mAdapter.addHeaderView(headerView);

    }


    private  GirdSpace girdSpace;


    @Override
    public void loadData() {
        super.loadData();
//
        new Handler().postDelayed(() -> {
            setData(DataUtil.getHomeGoods(8,0));
        }, 100);


    }










}
