package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeCategoryAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.home.Ad;
import com.cinderellavip.bean.net.home.HomeGoodsResult;
import com.cinderellavip.bean.net.home.ShopHomeResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.banner.BannerUtil;
import com.cinderellavip.util.banner.LinkUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.HomeTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.LazyListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopCategoryGoodsFragment extends LazyListFragment<HomeGoods> {

    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题
    @BindView(R.id.iv_ad_left)
    ImageView iv_ad_left;
    @BindView(R.id.iv_ad_right)
    ImageView iv_ad_right;

    @BindView(R.id.xbanner)
    XBanner xbanner;
    @BindView(R.id.scroll_recycler_view)
    RecyclerView scrollRecyclerView;
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    @BindView(R.id.tab_label)
    HomeTabLayout tabLabel;
    @BindView(R.id.appbar)
    AppBarLayout appbar;


    private HomeCategoryAdapter homeCategoryAdapter;


    private HomeCategoryItem homeCategoryItem;
    public static ShopCategoryGoodsFragment newInstance(HomeCategoryItem homeCategoryItem){
        ShopCategoryGoodsFragment cartFragment = new ShopCategoryGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("homeCategoryItem",homeCategoryItem);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
    @Override
    public int setLayout() {
        return R.layout.fragment_shop_category_goods;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        homeCategoryItem = getArguments().getParcelable("homeCategoryItem");
        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10), 2);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        scrollRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,5));
        homeCategoryAdapter = new HomeCategoryAdapter(homeCategoryItem);
        scrollRecyclerView.setAdapter(homeCategoryAdapter);

        List<String> data1 = new ArrayList<>();
        data1.add("推荐");
        data1.add("精选");
        data1.add("拼团");
        data1.add("进口");
        tabLabel.setTitle(data1);

    }


    private String goods_type = "5";
    @Override
    public void loadData1() {
        super.loadData();
        if (!isLoad || page == DEFAULT_PAGE){
            getCategory();
        }
        getGoods();
    }

    private void getGoods(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("type", goods_type);
        hashMap.put("first_category_id", ""+homeCategoryItem.id);
        hashMap.put("limit", PageSize+"");
        hashMap.put("page", page+"");
        new RxHttp<BaseResult<HomeGoodsResult>>().send(ApiManager.getService().getHomeGoods(hashMap),
                new Response<BaseResult<HomeGoodsResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeGoodsResult> result) {
                        setData(result.data.products);
                    }
                });

    }

    /**
     * 获取一级分类
     */
    private void getCategory(){
        new RxHttp<BaseResult<ShopHomeResult>>().send(ApiManager.getService().getHome(""+homeCategoryItem.id),
                new Response<BaseResult<ShopHomeResult>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<ShopHomeResult> result) {
                        if (!isAdded()){
                            return;
                        }
                        ShopHomeResult homeResult = result.data;
                        if (homeResult.banners == null || homeResult.banners.size() == 0){
                            rl_banner.setVisibility(View.GONE);
                        }else {
                            rl_banner.setVisibility(View.VISIBLE);
                            BannerUtil.setData(mActivity,xbanner,homeResult.banners);
                        }

                        List<HomeCategoryItem> typeList = homeResult.third_categories;
                        if (homeCategoryItem.id != 0 && typeList.size()>0){
                            typeList.add(new HomeCategoryItem("-1"));
                        }
                        homeCategoryAdapter.setNewData(typeList);

                        List<Ad> advertisements = homeResult.advertisements;
                        if (advertisements != null){
                            if (advertisements.size() == 0){
                                iv_ad_left.setVisibility(View.GONE);
                                iv_ad_right.setVisibility(View.GONE);
                            }else {
                                if (advertisements.size() == 1){
                                    iv_ad_left.setVisibility(View.VISIBLE);
                                    iv_ad_right.setVisibility(View.INVISIBLE);

                                }else if (advertisements.size() == 2){
                                    iv_ad_left.setVisibility(View.VISIBLE);
                                    iv_ad_right.setVisibility(View.VISIBLE);
                                    Ad ad = advertisements.get(1);
                                    ImageUtil.loadNet(mActivity,iv_ad_right,ad.img);
                                    iv_ad_right.setOnClickListener(view -> {
                                        LinkUtil.setData(mActivity,ad.type,ad.value);
                                    });
                                }
                                Ad ad = advertisements.get(0);
                                ImageUtil.loadNet(mActivity,iv_ad_left, ad.img);
                                iv_ad_left.setOnClickListener(view -> {
                                    LinkUtil.setData(mActivity,ad.type,ad.value);
                                });
                            }
                        }

                    }
                });

    }



    @OnClick({R.id.iv_top})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_top:
                mRecyclerView.scrollToPosition(0);
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
            if (position == 0){
                goods_type = "5";
                if (mAdapter != null){
                    ((HomeGoodsAdapter)mAdapter).setType(HomeGoodsAdapter.RECOMMEND);
                }
            }else {
                if (mAdapter != null){
                    ((HomeGoodsAdapter)mAdapter).setType(position-1);
                }
                goods_type = position+"";
            }

            page = DEFAULT_PAGE;
            getGoods();
        });

    }



}
