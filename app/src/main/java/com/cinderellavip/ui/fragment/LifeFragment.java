package com.cinderellavip.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.LifeAdapter;
import com.cinderellavip.adapter.recycleview.LifeCategoryAdapter;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.bean.net.life.LiftHomeAd;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.bean.net.life.LiftHomeListItem;
import com.cinderellavip.bean.net.life.LiftHomeResult;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.map.LocationUtil;
import com.cinderellavip.ui.activity.life.SearchLifeActivity;
import com.cinderellavip.ui.activity.life.SelectCityActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.util.ScreenUtil;
import com.cinderellavip.util.banner.BannerUtil;
import com.lishide.recyclerview.scroll.ScrollRecyclerView;
import com.stx.xhb.xbanner.XBanner;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class LifeFragment extends BaseListFragment<LiftHomeListItem> {

    @BindView(R.id.ll_title)
    LinearLayout llTitle; //最上面的标题
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_address)
    TextView tv_address;

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

        location();


    }

    private XBanner xbanner;  //banner
    //首页的分类
    private ScrollRecyclerView mBannerRecyclerView;
    //首页的分类的适配器
    private LifeCategoryAdapter homeCategoryAdapter;
    //首页分类的指示器
    private View viewIndicator;
    private RelativeLayout rlIndicator;
    private ImageView iv_ad1;
    private ImageView iv_ad2;
    private ImageView iv_ad3;

    private void initHeadView() {
        View headerView = View.inflate(mActivity, R.layout.header_life, null);
        mBannerRecyclerView = headerView.findViewById(R.id.scroll_recycler_view);
        viewIndicator = headerView.findViewById(R.id.view_indicator);
        rlIndicator = headerView.findViewById(R.id.rl_indicator);
        xbanner = headerView.findViewById(R.id.xbanner);
        iv_ad1 = headerView.findViewById(R.id.iv_ad1);
        iv_ad2 = headerView.findViewById(R.id.iv_ad2);
        iv_ad3 = headerView.findViewById(R.id.iv_ad3);
        iv_ad1.setOnClickListener(this::onClick);
        iv_ad2.setOnClickListener(this::onClick);
        iv_ad3.setOnClickListener(this::onClick);
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
        getData(CinderellApplication.name);

    }
    private void location(){
        LocationUtil.getInstance().start(mActivity,(aMapLocation, lat, lnt) -> {
            if (aMapLocation.getErrorCode() == 0){
                String city = aMapLocation.getCity();
                String replaceAll = city.replaceAll("市", "");
                tv_address.setText(TextUtils.isEmpty(replaceAll)?"南京": replaceAll);
            }else {
                tv_address.setText("南京");
            }
            CinderellApplication.name = tv_address.getText().toString().trim();
            getData(CinderellApplication.name);

        });
    }


    LiftHomeResult liftHomeResult;
    private void getData(String city){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("city", city);
        new RxHttp<BaseResult<LiftHomeResult>>().send(ApiManager.getService().life_home(hashMap),
                new Response<BaseResult<LiftHomeResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<LiftHomeResult> result) {
                       liftHomeResult = result.data;
                       setData();
                       setData(true,liftHomeResult.list);
                    }
                });
    }

    private void setData(){
        BannerUtil.setData(mActivity,xbanner,liftHomeResult.banner);
        List<LiftHomeCategory> typeList = getSortCategory(liftHomeResult.topJust);
        homeCategoryAdapter.setNewData(typeList);
        if (typeList == null || typeList.size() <= 10) {
            //如果小于10则 宽度一样
            ViewGroup.LayoutParams linearParams = viewIndicator.getLayoutParams();
            ViewGroup.LayoutParams linearParams1 = rlIndicator.getLayoutParams();
            linearParams1.width = linearParams.width;
            rlIndicator.setLayoutParams(linearParams1);
        } else {
            //为了计算大于10的时候 滑动的距离
            categoryNumber = typeList.size();
        }

        List<LiftHomeAd> billing = liftHomeResult.billing;
        if (billing != null && billing.size() == 1){
            iv_ad1.setVisibility(View.VISIBLE);
            iv_ad2.setVisibility(View.GONE);
            iv_ad3.setVisibility(View.GONE);
            LiftHomeAd liftHomeAd = billing.get(0);
            ImageUtil.loadNet(mActivity,iv_ad1,liftHomeAd.img);
        }else if (billing != null && billing.size() == 2){
            iv_ad1.setVisibility(View.VISIBLE);
            iv_ad2.setVisibility(View.VISIBLE);
            iv_ad3.setVisibility(View.INVISIBLE);
            ImageUtil.loadNet(mActivity,iv_ad1,billing.get(0).img);
            ImageUtil.loadNet(mActivity,iv_ad2,billing.get(1).img);
        }else if (billing != null && billing.size() == 3){
            iv_ad1.setVisibility(View.VISIBLE);
            iv_ad2.setVisibility(View.VISIBLE);
            iv_ad3.setVisibility(View.VISIBLE);
            ImageUtil.loadNet(mActivity,iv_ad1,billing.get(0).img);
            ImageUtil.loadNet(mActivity,iv_ad2,billing.get(1).img);
            ImageUtil.loadNet(mActivity,iv_ad3,billing.get(2).img);
        }

    }

    private List<LiftHomeCategory> getSortCategory(List<LiftHomeCategory> list){
        List<LiftHomeCategory> sortList = new ArrayList<>();
        int column = list.size()/2+list.size()%2;
        for (int i=0;i<column;i++){
            sortList.add(list.get(i));
            if (i+column<list.size())
                sortList.add(list.get(i+column));
        }
        return sortList;
    }

    @Override
    public void initListener() {
        //刷新
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);
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
            case R.id.iv_ad1:
                if (liftHomeResult != null)
                    AgreementWebViewActivity.launch(mActivity,liftHomeResult.billing.get(0).url);
                break;
            case R.id.iv_ad2:
                if (liftHomeResult != null)
                    AgreementWebViewActivity.launch(mActivity,liftHomeResult.billing.get(1).url);
                break;
            case R.id.iv_ad3:
                if (liftHomeResult != null)
                    AgreementWebViewActivity.launch(mActivity,liftHomeResult.billing.get(2).url);
                break;
        }
    }

    public void setAddress(String name) {
        CinderellApplication.name = name;
        tv_address.setText(name);
        getData(name);
    }
}
