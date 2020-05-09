package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeCategoryAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.net.home.HomeBanner;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.HomeTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopCategoryGoodsFragment extends BaseListFragment<HomeGoods> {
    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题


    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.scroll_recycler_view)
    ScrollRecyclerView scrollRecyclerView;
    @BindView(R.id.tab_label)
    HomeTabLayout tabLabel;
    @BindView(R.id.appbar)
    AppBarLayout appbar;


    private HomeCategoryAdapter homeCategoryAdapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_shop_category_goods;
    }

    @Override
    public void loadData() {

        initBanner(DataUtil.getBannerData(4));


        List<String> data1 = new ArrayList<>();
        data1.add("精选");
        data1.add("拼团");
        data1.add("进口");
        data1.add("实惠");
        tabLabel.setTitle(data1);

//        homeCategoryAdapter.setNewData(DataUtil.getShopOtherCategory());

//        LogUtil.e("page = " + page + "," + "PageSize = " + PageSize);
        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getHomeGoods(4));
        }, 100);
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(true);

        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
//                LogUtil.e("totalDy"+totalDy);
                if (totalDy > 100) {
                    iv_top.setVisibility(View.VISIBLE);
                } else {
                    iv_top.setVisibility(View.GONE);
                }
            }
        });
        //SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            if (i >= 0) {
                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeLayout.setEnabled(false); //否则关闭
            }
        });

        tabLabel.setOnTabPositionClickLister(position -> {
            page = 0;
            setData(DataUtil.getHomeGoods(4,position));
        });

    }





    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10), 2);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        initHeadView();
    }


    private void initHeadView() {
        scrollRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryAdapter = new HomeCategoryAdapter();
        scrollRecyclerView.setAdapter(homeCategoryAdapter);

    }

    private void initBanner(List<HomeBanner> data) {
        xbanner.setBannerData(R.layout.item_home_banner, data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageView image = view.findViewById(R.id.image);

        }
        ));
    }

    @OnClick({R.id.iv_top})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_top:
                mRecyclerView.smoothScrollToPosition(0);
                CoordinatorLayout.Behavior behavior =
                        ((CoordinatorLayout.LayoutParams) appbar.getLayoutParams()).getBehavior();
                if (behavior instanceof AppBarLayout.Behavior) {
                    AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                    int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                    if (topAndBottomOffset != 0) {
                        appBarLayoutBehavior.setTopAndBottomOffset(0);
                    }
                }
                iv_top.setVisibility(View.GONE);
                break;
        }
    }


}
