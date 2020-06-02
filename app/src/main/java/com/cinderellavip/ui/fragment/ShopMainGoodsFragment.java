package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeCategoryAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.adapter.recycleview.SpikeHomeAdapter;
import com.cinderellavip.bean.eventbus.UpdateHomeMainData;
import com.cinderellavip.bean.eventbus.UpdateShopPage;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.home.Ad;
import com.cinderellavip.bean.net.home.HomeGoodsResult;
import com.cinderellavip.bean.net.home.HomeSpike;
import com.cinderellavip.bean.net.home.ShopHomeResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.GoodsListActivity;
import com.cinderellavip.ui.activity.home.SpikeListActivity;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.util.banner.BannerUtil;
import com.cinderellavip.util.banner.LinkUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.HomeTabLayout;
import com.cinderellavip.weight.HorSpace;
import com.google.android.material.appbar.AppBarLayout;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.LazyListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopMainGoodsFragment extends LazyListFragment<HomeGoods> {

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
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    @BindView(R.id.tab_label)
    HomeTabLayout tabLabel;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @BindView(R.id.ll_spike)
    LinearLayout ll_spike;
    @BindView(R.id.tv_hour)
    TextView tv_hour;
    @BindView(R.id.tv_minute)
    TextView tv_minute;
    @BindView(R.id.rv_spike)
    RecyclerView rv_spike;
    private SpikeHomeAdapter spikeHomeAdapter;


    private HomeCategoryAdapter homeCategoryAdapter;


    public static ShopMainGoodsFragment newInstance(HomeCategoryItem homeCategoryItem){
        ShopMainGoodsFragment cartFragment = new ShopMainGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("homeCategoryItem",homeCategoryItem);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }
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
        //设置分类
        scrollRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryAdapter = new HomeCategoryAdapter(item -> {
            if ( "3".equals(item.type) || "2".equals(item.type)){
                GoodsListActivity.launch(getContext(),item.name,item.id);
            }else if ("1".equals(item.type)){
                EventBus.getDefault().post(new UpdateShopPage(item.id+""));
            }

        });
        scrollRecyclerView.setAdapter(homeCategoryAdapter);
        //设置秒杀
        rv_spike.setLayoutManager(new LinearLayoutManager(mActivity,RecyclerView.HORIZONTAL,false));
        HorSpace spikeSpace = new HorSpace(DpUtil.dip2px(mActivity, 12));
        rv_spike.addItemDecoration(spikeSpace);
        spikeHomeAdapter = new SpikeHomeAdapter();
        rv_spike.setAdapter(spikeHomeAdapter);

        //设置tab
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
        hashMap.put("first_category_id", "0");
        hashMap.put("limit", PageSize+"");
        hashMap.put("page", page+"");
        new RxHttp<BaseResult<HomeGoodsResult>>().send(ApiManager.getService().getHomeGoods(hashMap),
                new Response<BaseResult<HomeGoodsResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<HomeGoodsResult> result) {
                        setData(result.data.products);
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

    /**
     * 获取一级分类
     */
    private void getCategory(){
        new RxHttp<BaseResult<ShopHomeResult>>().send(ApiManager.getService().getHome("0"),
                new Response<BaseResult<ShopHomeResult>>(mActivity,Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<ShopHomeResult> result) {
                        ShopHomeResult homeResult = result.data;
                        if (homeResult.banners == null || homeResult.banners.size() == 0){
                            rl_banner.setVisibility(View.GONE);
                        }else {
                            rl_banner.setVisibility(View.VISIBLE);
                            BannerUtil.setData(mActivity,xbanner,homeResult.banners);
                        }
                        List<HomeCategoryItem> typeList = getSortCategory(homeResult.third_categories);
                        homeCategoryAdapter.setNewData(typeList);
                        if (typeList == null || typeList.size()<=10){
                            //如果小于10则 宽度一样
                            ViewGroup.LayoutParams linearParams = viewIndicator.getLayoutParams();
                            ViewGroup.LayoutParams linearParams1 = rlIndicator.getLayoutParams();
                            linearParams1.width = linearParams.width;
                            rlIndicator.setLayoutParams(linearParams1);
                            rlIndicator.setVisibility(View.GONE);
                        }else {
                            //为了计算大于10的时候 滑动的距离
                            rlIndicator.setVisibility(View.VISIBLE);
                            categoryNumber = typeList.size();
                        }
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
                        HomeSpike spikes = homeResult.spikes;
                        if (spikes != null && spikes.list != null && spikes.list.size()>0){
                            ll_spike.setVisibility(View.VISIBLE);
                            tv_hour.setText(spikes.time.split(":")[0]);
                            tv_minute.setText(spikes.time.split(":")[1]);
                            spikeHomeAdapter.setNewData(spikes.list);
                        }else {
                            ll_spike.setVisibility(View.GONE);
                        }


                    }
                });

    }


    private List<HomeCategoryItem> getSortCategory(List<HomeCategoryItem> list){
        List<HomeCategoryItem> sortList = new ArrayList<>();
        int column = list.size()/2+list.size()%2;
        for (int i=0;i<column;i++){
            sortList.add(list.get(i));
            if (i+column<list.size())
                sortList.add(list.get(i+column));
        }
        return sortList;
    }


    @OnClick({R.id.iv_top,R.id.tv_spike_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_spike_more:
                SpikeListActivity.launch(mActivity);
                break;
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

    int categoryNumber = 0;
    private void setViewIndicator(int total, int precent) {
        int width = rlIndicator.getWidth();
        int width1 = viewIndicator.getWidth();
        int distanse = width - width1;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewIndicator.getLayoutParams();
        layoutParams.leftMargin = (int) (precent * 1.0 / total * distanse);
        viewIndicator.setLayoutParams(layoutParams);

    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof UpdateHomeMainData){
            onRefresh();
        }
    }
}
