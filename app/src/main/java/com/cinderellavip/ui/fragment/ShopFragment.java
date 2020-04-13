package com.cinderellavip.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeCategoryAdapter;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.HomeBanner;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.weight.GirdSpace;
import com.cinderellavip.weight.HomeTabLayout;
import com.cinderellavip.weight.MyTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.ui.BaseList1Fragment;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopFragment extends BaseListFragment<String> {
    @BindView(R.id.ll_title)
    LinearLayout llTitle; //最上面的标题
    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题
    //headerview
    private XBanner xbanner;  //banner
    private MyTabLayout tab_category;  //banner
    private HomeTabLayout tab_label;  //banner

    //首页的分类
    private ScrollRecyclerView mBannerRecyclerView;
    //首页的分类的适配器
    private HomeCategoryAdapter homeCategoryAdapter;
    //首页分类的指示器
    private View viewIndicator;
    private RelativeLayout rlIndicator;

    int number = 15;


    @Override
    public int setLayout() {
        return R.layout.fragment_shop;
    }

    @Override
    public void loadData() {

        initBanner(DataUtil.getBannerData(4));

        List<String> data = new ArrayList<>();
        data.add("首页");
        data.add("食品生鲜");
        data.add("居家百货");
        data.add("美妆日化");
        data.add("数码电气");
        data.add("母婴产品");
        data.add("休闲娱乐");
        tab_category.setTitle(data);

        List<String> data1 = new ArrayList<>();
        data1.add("精选");
        data1.add("拼团");
        data1.add("进口");
        data1.add("实惠");
        tab_label.setTitle(data1);


        List<String> list = new ArrayList<>();
        for (int i = 1; i < number; i++) {
            list.add(i + "");
        }
        homeCategoryAdapter.setNewData(list);

        LogUtil.e("page = "+page+","+"PageSize = "+PageSize);
        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(()->{
            setData(DataUtil.getData(4));
        },100);
    }

    @Override
    public void initListener() {
        super.initListener();
        swipeLayout.setProgressViewEndTarget(false, DpUtil.dip2px(mActivity, 120));
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);

        mBannerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dx;
                int screenWidth = ScreenUtil.getScreenWidth(mActivity);
                setViewIndicator(2 * screenWidth / 5, totalDy);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                if (totalDy>1000){
                    iv_top.setVisibility(View.VISIBLE);
                }else {
                    iv_top.setVisibility(View.GONE);
                }
                float percent = Math.abs(totalDy) * 1.0f / 300 % 100;
                percent = percent > 1 ? 1 : percent;
                String bgColor = ColorUtil.caculateColor("#00000000", "#FFFF0000", percent);
                llTitle.setBackgroundColor(Color.parseColor(bgColor));
            }
        });

        tab_category.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    // 改变 tab 选择状态下的字体大小
                    ((TextView) view).setTextSize(25);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    // 改变 tab 未选择状态下的字体大小
                    ((TextView) view).setTextSize(15);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setViewIndicator(int total, int precent) {
        int width = rlIndicator.getWidth();
        int width1 = viewIndicator.getWidth();
        int distanse = width - width1;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewIndicator.getLayoutParams();
        layoutParams.leftMargin = (int) (precent * 1.0 / total * distanse);
        viewIndicator.setLayoutParams(layoutParams);

    }

    private int i = 0;


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity,10),2,1);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
        initHeadView();
    }


    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_home, null);
        mBannerRecyclerView = headerView.findViewById(R.id.scroll_recycler_view);
        viewIndicator = headerView.findViewById(R.id.view_indicator);
        rlIndicator = headerView.findViewById(R.id.rl_indicator);
        xbanner = headerView.findViewById(R.id.xbanner);
        tab_category = headerView.findViewById(R.id.tab_category);
        tab_label = headerView.findViewById(R.id.tab_label);
        mBannerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBannerRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryAdapter = new HomeCategoryAdapter();
        mBannerRecyclerView.setAdapter(homeCategoryAdapter);
         mAdapter.addHeaderView(headerView);


    }

    private void initBanner(List<HomeBanner> data) {
        xbanner.setBannerData(R.layout.item_home_banner, data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageView image = view.findViewById(R.id.image);

        }
        ));
    }
    @OnClick({R.id.iv_kefu,  R.id.iv_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_kefu:
                break;
            case R.id.iv_top:
                mRecyclerView.smoothScrollToPosition(0);
                break;
        }
    }
}
