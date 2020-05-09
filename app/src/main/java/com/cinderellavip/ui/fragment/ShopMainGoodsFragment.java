package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeCategoryAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.home.Ad;
import com.cinderellavip.bean.net.home.ShopHomeResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.util.banner.BannerUtil;
import com.cinderellavip.util.banner.LinkUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.HomeTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.http.RxHttp;
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


public class ShopMainGoodsFragment extends BaseListFragment<HomeGoods> {

    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题
    @BindView(R.id.iv_ad_left)
    ImageView iv_ad_left;
    @BindView(R.id.iv_ad_right)
    ImageView iv_ad_right;


    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.scroll_recycler_view)
    ScrollRecyclerView scrollRecyclerView;
    @BindView(R.id.view_indicator)
    View viewIndicator;
    @BindView(R.id.rl_indicator)
    RelativeLayout rlIndicator;
    @BindView(R.id.tab_label)
    HomeTabLayout tabLabel;
    @BindView(R.id.appbar)
    AppBarLayout appbar;


    private HomeCategoryAdapter homeCategoryAdapter;

    @Override
    public int setLayout() {
        return R.layout.fragment_shop_home_goods;
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

        scrollRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryAdapter = new HomeCategoryAdapter();
        scrollRecyclerView.setAdapter(homeCategoryAdapter);
    }

    @Override
    public void loadData() {


        List<String> data1 = new ArrayList<>();
        data1.add("精选");
        data1.add("拼团");
        data1.add("进口");
        data1.add("实惠");
        tabLabel.setTitle(data1);





//        LogUtil.e("page = " + page + "," + "PageSize = " + PageSize);
        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getHomeGoods(4,0));
        }, 100);

        getCategory();
    }


    /**
     * 获取一级分类
     */
    private void getCategory(){
        new RxHttp<BaseResult<ShopHomeResult>>().send(ApiManager.getService().getHome("0"),
                new Response<BaseResult<ShopHomeResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ShopHomeResult> result) {
                        ShopHomeResult homeResult = result.data;


                        BannerUtil.setData(mActivity,xbanner,homeResult.banners);

                        List<HomeCategoryItem> typeList = homeResult.third_categories;
                        homeCategoryAdapter.setNewData(typeList);
                        if (typeList == null || typeList.size()<=10){
                            //如果小于10则 宽度一样
                            ViewGroup.LayoutParams linearParams = viewIndicator.getLayoutParams();
                            ViewGroup.LayoutParams linearParams1 = rlIndicator.getLayoutParams();
                            linearParams1.width = linearParams.width;
                            rlIndicator.setLayoutParams(linearParams1);
                        }else {
                            //为了计算大于10的时候 滑动的距离
                            categoryNumber = typeList.size();
                        }

                        List<Ad> advertisements = homeResult.advertisements;
                        if (advertisements != null && advertisements.size()>0){
                            Ad ad = advertisements.get(0);

                            ImageUtil.loadNet(mActivity,iv_ad_left, ad.img);
                            iv_ad_left.setOnClickListener(view -> {
                                LinkUtil.setData(mActivity,ad.type,ad.value);
                            });
                        }
                        if (advertisements != null && advertisements.size()>1){
                            Ad ad = advertisements.get(1);
                            ImageUtil.loadNet(mActivity,iv_ad_right,ad.img);
                            iv_ad_right.setOnClickListener(view -> {
                                LinkUtil.setData(mActivity,ad.type,ad.value);
                            });
                        }


                    }
                });

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


    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setEnabled(true);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        scrollRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dx;
//                LogUtil.e("onScrolled"+totalDy);
                if (categoryNumber>10){
                    int screenWidth = ScreenUtil.getScreenWidth(mActivity);
                    setViewIndicator(((categoryNumber-10)%2+(categoryNumber-10)/2) * screenWidth / 5, totalDy);
                }
            }
        });
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
        // SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            if (i >= 0) {
                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeLayout.setEnabled(false); //否则关闭
            }
        });

        tabLabel.setOnTabPositionClickLister(position -> {
            new Handler().postDelayed(() -> {
                page = 0;
                setData(DataUtil.getHomeGoods(4,position));
            }, 100);
        });

    }

    int categoryNumber = 0;
    private void setViewIndicator(int total, int precent) {
        int width = rlIndicator.getWidth();
        int width1 = viewIndicator.getWidth();
        int distanse = width - width1;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewIndicator.getLayoutParams();
        layoutParams.leftMargin = (int) (precent * 1.0 / total * distanse);
        viewIndicator.setLayoutParams(layoutParams);

    }


}
