package com.cinderellavip.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.BrandInfo;
import com.cinderellavip.bean.net.BrandResult;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.OnSureClickListener;
import com.cinderellavip.ui.activity.home.SearchListActivity;
import com.cinderellavip.util.ColorUtil;
import com.cinderellavip.weight.FilterView;
import com.cinderellavip.weight.GirdSpace;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class BrandDetailFragment extends BaseListFragment<HomeGoods> implements OnSureClickListener {
    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题


    @BindView(R.id.appbar)
    AppBarLayout appbar;


    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_collect)
    ImageView iv_collect;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.title)
    LinearLayout title;


    @BindView(R.id.tv_title_name)
    TextView tv_title_name;

    @BindView(R.id.filter_view)
    FilterView filter_view;

    public static BrandDetailFragment newInstance(String id) {
        BrandDetailFragment cartFragment = new BrandDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    private String id;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getString("id");

        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10), 2, 0, true);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        filter_view.setTv_comment("新品");
    }


    @Override
    public int setLayout() {
        return R.layout.activity_brand_detail;
    }


    public String sort = "0", sort_type = "0";

    @Override
    public void loadData() {

        getBrandInfo();

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("brand_id", "" + id);
        hashMap.put("sort", sort);
        hashMap.put("sort_type", sort_type);
        hashMap.put("limit", "" + PageSize);
        hashMap.put("page", "" + page);
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getBrandGoods(hashMap),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad, getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });


    }

    private BrandInfo storeInfo;
    private void getBrandInfo() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("brand_id", id + "");
        new RxHttp<BaseResult<BrandResult>>().send(ApiManager.getService().getBrandInfo(hashMap),
                new Response<BaseResult<BrandResult>>(getContext(),Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<BrandResult> result) {
                        storeInfo = result.data.brand_info;
                        tv_title_name.setText(storeInfo.name);
                        ImageUtil.loadNet(mActivity, ivImage, storeInfo.image);
                        if (storeInfo.collect) {
                            iv_collect.setImageResource(R.mipmap.brand_collect_select);
                        } else {
                            iv_collect.setImageResource(R.mipmap.brand_collect);
                        }
                        if (TextUtils.isEmpty(storeInfo.video)){
                            llVideo.setVisibility(View.GONE);
                        }else {
                            llVideo.setVisibility(View.VISIBLE);
                        }

                    }
                });
    }

    @Override
    public void initListener() {
        super.initListener();
        filter_view.setOnDialogClickListener(this);
        swipeLayout.setEnabled(true);

        mAdapter.getLoadMoreModule().setEnableLoadMore(false);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                LogUtil.e("totalDy" + totalDy);
                if (totalDy > 100) {
                    iv_top.setVisibility(View.VISIBLE);
                } else {
                    iv_top.setVisibility(View.GONE);
                }

            }
        });
        //SwipeRefreshLayout和CoordinatorLayout滑动冲突
        appbar.addOnOffsetChangedListener((AppBarLayout.BaseOnOffsetChangedListener) (appBarLayout, i) -> {
            LogUtil.e("totalDy" + i);
            if (i >= 0) {
                swipeLayout.setEnabled(true); //当滑动到顶部的时候开启
            } else {
                swipeLayout.setEnabled(false); //否则关闭
            }
            float percent = Math.abs(i) * 1.0f / 200 % 100;
            percent = percent > 1 ? 1 : percent;
            String bgColor = ColorUtil.caculateColor("#00000000", "#FFFFFFFF", percent);
            title.setBackgroundColor(Color.parseColor(bgColor));
            String textColor = ColorUtil.caculateColor("#00000000", "#FF111111", percent);
            tv_title_name.setTextColor(Color.parseColor(textColor));
        });


    }


    @OnClick({R.id.ll_back, R.id.ll_search, R.id.rl_collect,R.id.iv_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                mActivity.finish();
                break;
            case R.id.ll_search:
                SearchListActivity.launch(mActivity,id,SearchListActivity.BRAND);
                break;
            case R.id.rl_collect:
                collect();
                break;
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

    private void collect(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("id", id + "");
        hashMap.put("type",   "2");
        new RxHttp<BaseResult>().send(ApiManager.getService().getCollect(hashMap),
                new Response<BaseResult>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        storeInfo.collect = !storeInfo.collect;
                        if (storeInfo.collect) {
                            iv_collect.setImageResource(R.mipmap.brand_collect_select);
                        } else {
                            iv_collect.setImageResource(R.mipmap.brand_collect);
                        }


                    }
                });

    }

    @Override
    public void onSure() {
        this.sort = filter_view.getSort()+"";
        this.sort_type = filter_view.getSort_type()+"";
        swipeLayout.setRefreshing(true);
        onRefresh();
    }
}
