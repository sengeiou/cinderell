package com.cinderellavip.ui.fragment.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SelectServiceCouponAdapter;
import com.cinderellavip.adapter.recycleview.SeleteCoupondapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class SelectServiceCouponFragment extends BaseListFragment<CouponsBean> {


    public static SelectServiceCouponFragment newInstance( ) {
        SelectServiceCouponFragment cartFragment = new SelectServiceCouponFragment();
        Bundle bundle = new Bundle();

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

        setEmptyView(R.mipmap.empty_view_coupon,"您还没有优惠券哦~");


    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<CouponsBean> list = new ArrayList<>();
            list.add(new CouponsBean(CouponsBean.NORMAL));
            list.add(new CouponsBean(CouponsBean.NORMAL));
            list.add(new CouponsBean(CouponsBean.NORMAL));
            list.add(new CouponsBean(CouponsBean.NORMAL));
            setData(list);
        },500);


    }

    @Override
    public void initListener() {
        super.initListener();

        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<CouponsBean> data = mAdapter.getData();
            CouponsBean couponsBean = data.get(position);
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
