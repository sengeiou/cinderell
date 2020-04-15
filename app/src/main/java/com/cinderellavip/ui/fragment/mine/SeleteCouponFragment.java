package com.cinderellavip.ui.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;


import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SeleteCoupondapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.tozzais.baselibrary.ui.BaseListFragment;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class SeleteCouponFragment extends BaseListFragment<CouponsBean> {


    public static SeleteCouponFragment newInstance( ) {
        SeleteCouponFragment cartFragment = new SeleteCouponFragment();
        Bundle bundle = new Bundle();

        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_selete_coupon;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new SeleteCoupondapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无优惠券可使用");


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

    @OnClick(R.id.tv_btn_nouse)
    public void onClick() {
        CouponsBean couponsBean = new CouponsBean();
        Intent intent = new Intent();
        intent.putExtra("couponsBean", couponsBean);
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();

    }
}
