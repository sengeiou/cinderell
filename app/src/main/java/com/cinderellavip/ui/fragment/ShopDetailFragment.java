package com.cinderellavip.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.ShopInfo;
import com.cinderellavip.bean.net.ShopResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.weight.GirdSpace;
import com.google.android.material.appbar.AppBarLayout;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.DpUtil;

import java.util.TreeMap;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class ShopDetailFragment extends BaseListFragment<HomeGoods> {

    @BindView(R.id.iv_top)
    ImageView iv_top; //最上面的标题


    @BindView(R.id.appbar)
    AppBarLayout appbar;


    public static ShopDetailFragment newInstance(String id) {
        ShopDetailFragment cartFragment = new ShopDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }



    public String sort = "0", sort_type = "0";

    private  String id;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        id = getArguments().getString("id");

        getShopInfo();


        //设置商品
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 10), 2,0,true);
        mRecyclerView.addItemDecoration(girdSpace);
        mAdapter = new HomeGoodsAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_shop_detail;
    }

    @Override
    public void loadData() {


        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", "" + id);
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

    private ShopInfo storeInfo;
    private void getShopInfo() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", id + "");
        new RxHttp<BaseResult<ShopResult>>().send(ApiManager.getService().getShopInfo(hashMap),
                new Response<BaseResult<ShopResult>>(getContext(),Response.BOTH) {
                    @Override
                    public void onSuccess(BaseResult<ShopResult> result) {
                        storeInfo = result.data.store_info;
                        ShopDetailActivity mActivity = (ShopDetailActivity) ShopDetailFragment.this.mActivity;
                        mActivity.setTvTitleName(storeInfo.name);
                        mActivity.setIvCollect(storeInfo.collect);

//                        tv_title_name.setText(storeInfo.name);
//                        ImageUtil.loadNet(mActivity, ivImage, storeInfo.image);
//                        if (storeInfo.collect) {
//                            iv_collect.setImageResource(R.mipmap.brand_collect_select);
//                        } else {
//                            iv_collect.setImageResource(R.mipmap.brand_collect);
//                        }
//                        if (TextUtils.isEmpty(storeInfo.video)){
//                            llVideo.setVisibility(View.GONE);
//                        }else {
//                            llVideo.setVisibility(View.VISIBLE);
//                        }

                    }
                });
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
