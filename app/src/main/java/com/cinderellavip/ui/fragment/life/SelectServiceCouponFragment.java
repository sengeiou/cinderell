package com.cinderellavip.ui.fragment.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectServiceCouponAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.net.life.LifeCoupon;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.life.ServiceSelectCouponActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class SelectServiceCouponFragment extends BaseListFragment<LifeCoupon> {


    public static SelectServiceCouponFragment newInstance( String contracts_id,int type) {
        SelectServiceCouponFragment cartFragment = new SelectServiceCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contracts_id",contracts_id);
        bundle.putInt("type",type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_selete_coupon_for_service;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new SelectServiceCouponAdapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView(R.mipmap.empty_view_coupon,"暂无可用优惠券~");


        contracts_id = getArguments().getString("contracts_id");
        type = getArguments().getInt("type");

    }

    private String contracts_id;
    private int type;
    @Override
    public void loadData() {
        super.loadData();
        if (type == ServiceSelectCouponActivity.LONG){
            getLongCoupons();
        }else if (type == ServiceSelectCouponActivity.PROJECT){
            getProjectCoupons();
        }


    }

    private void getLongCoupons(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("contracts_id", ""+contracts_id);
        new RxHttp<BaseResult<ListData<LifeCoupon>>>().send(ApiManager.getService().longOrderCanUse(hashMap),
                new Response<BaseResult<ListData<LifeCoupon>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<LifeCoupon>> result) {
                        setData(result.data.data);
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

    private void getProjectCoupons(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("project", ""+contracts_id);
        hashMap.put("city", ""+ CinderellApplication.name);
        new RxHttp<BaseResult<ListData<LifeCoupon>>>().send(ApiManager.getService().lifeCoupon(hashMap),
                new Response<BaseResult<ListData<LifeCoupon>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<LifeCoupon>> result) {
                        setData(result.data.data);
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

    @Override
    public void initListener() {
        if (swipeLayout != null)
            swipeLayout.setOnRefreshListener(this::onRefresh);

        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<LifeCoupon> data = mAdapter.getData();
            LifeCoupon couponsBean = data.get(position);
                Intent intent = new Intent();
                intent.putExtra("couponsBean", couponsBean);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();

        }));

    }

    @OnClick(R.id.tv_btn_no_use)
    public void onClick() {
        mActivity.setResult(Activity.RESULT_OK, null);
        mActivity.finish();

    }
}
