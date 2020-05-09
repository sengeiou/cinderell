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
import com.cinderellavip.adapter.recycleview.LifeAdapter;
import com.cinderellavip.adapter.recycleview.LifeCategoryAdapter;
import com.cinderellavip.bean.net.home.HomeBanner;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.activity.life.SearchLifeActivity;
import com.cinderellavip.ui.activity.life.SelectCityActivity;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.util.ScreenUtil;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class LifeFragment extends BaseListFragment<String> {

    @BindView(R.id.ll_title)
    LinearLayout llTitle; //最上面的标题
    @BindView(R.id.tv_search)
    TextView tv_search;

    @Override
    public int setLayout() {
        return R.layout.fragment_life;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        //设置商品
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new LifeAdapter();
        mRecyclerView.setAdapter(mAdapter);

        initHeadView();
    }

    private XBanner xbanner;  //banner
    //首页的分类
    private ScrollRecyclerView mBannerRecyclerView;
    //首页的分类的适配器
    private LifeCategoryAdapter homeCategoryAdapter;
    //首页分类的指示器
    private View viewIndicator;
    private RelativeLayout rlIndicator;

    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_life, null);
        mBannerRecyclerView = headerView.findViewById(R.id.scroll_recycler_view);
        viewIndicator = headerView.findViewById(R.id.view_indicator);
        rlIndicator = headerView.findViewById(R.id.rl_indicator);
        xbanner = headerView.findViewById(R.id.xbanner);
        mBannerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mBannerRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.HORIZONTAL));
        homeCategoryAdapter = new LifeCategoryAdapter();
        mBannerRecyclerView.setAdapter(homeCategoryAdapter);

        mAdapter.addHeaderView(headerView);


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
    public void loadData() {
        super.loadData();

        initBanner(DataUtil.getBannerData(4));

//        List<HomeCategoryItem> typeList = DataUtil.getLifeCategory();
//        homeCategoryAdapter.setNewData(typeList);
//        if (typeList == null || typeList.size() <= 10) {
//            //如果小于10则 宽度一样
//            ViewGroup.LayoutParams linearParams = viewIndicator.getLayoutParams();
//            ViewGroup.LayoutParams linearParams1 = rlIndicator.getLayoutParams();
//            linearParams1.width = linearParams.width;
//            rlIndicator.setLayoutParams(linearParams1);
//        } else {
//            //为了计算大于10的时候 滑动的距离
//            categoryNumber = typeList.size();
//        }


        //这里只有通过Handler 已经到底啦 才会出来
        new Handler().postDelayed(() -> {
            setData(DataUtil.getData(10));
        }, 100);
    }

    private void initBanner(List<HomeBanner> data) {
        xbanner.setBannerData(R.layout.item_home_banner, data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageView image = view.findViewById(R.id.image);
            image.setImageResource(R.mipmap.banner_life);
            image.setOnClickListener(v -> {
                WebViewActivity.launch(mActivity,"详情","https://www.baidu.com");
            });
            }
        ));
    }

    @Override
    public void initListener() {
        super.initListener();
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mBannerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dx;
                if (categoryNumber > 10) {
                    int screenWidth = ScreenUtil.getScreenWidth(mActivity);
                    setViewIndicator(((categoryNumber - 10) % 2 + (categoryNumber - 10) / 2) * screenWidth / 5, totalDy);
                }

            }
        });
        swipeLayout.setProgressViewEndTarget(false, DpUtil.dip2px(mActivity, 120));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                float percent = Math.abs(totalDy) * 1.0f / 300 % 100;
                percent = percent > 1 ? 1 : percent;
                String bgColor = ColorUtil.caculateColor("#00000000", "#FFF07700", percent);
                llTitle.setBackgroundColor(Color.parseColor(bgColor));
            }
        });


    }


    @OnClick({R.id.tv_address, R.id.tv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                SelectCityActivity.launch(mActivity);
                break;
            case R.id.tv_search:
                SearchLifeActivity.launch(mActivity);
                break;
        }
    }
}
