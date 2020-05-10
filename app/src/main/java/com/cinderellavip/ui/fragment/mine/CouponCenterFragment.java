package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CouponCenterAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.ui.activity.mine.MineCouponActivity;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class CouponCenterFragment extends BaseListFragment<CouponsBean> {


    @BindView(R.id.tv_add)
    TextView tvAdd;
    private int type;

    public static CouponCenterFragment newInstance(int type) {
        CouponCenterFragment cartFragment = new CouponCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_mine_coupon;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        type = getArguments().getInt("type");

        tvAdd.setText("我的\n优惠券");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new CouponCenterAdapter(type);
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无优惠券");


    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<CouponsBean> list = new ArrayList<>();
            list.add(new CouponsBean());
            list.add(new CouponsBean());
            list.add(new CouponsBean());
            setData(list);
        },500);




    }

    @Override
    public void initListener() {
        super.initListener();

    }

    @OnClick(R.id.tv_add)
    public void onClick() {
        MineCouponActivity.launch(mActivity);
    }


}
