package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineCouponAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.ui.activity.mine.CouponCenterActivity;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class MineCouponFragment extends BaseListFragment<CouponsBean> {

    @BindView(R.id.tv_add)
    TextView tvAdd;

    public static final int UNUSED = 0, USED = 1, EXPIRED = 2;
    private int type;

    public static MineCouponFragment newInstance(int type) {
        MineCouponFragment cartFragment = new MineCouponFragment();
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
        tvAdd.setText("领更多\n好券");
        type = getArguments().getInt("type");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.addItemDecoration(new LineItemDecoration(2, R.color.line));
        mAdapter = new MineCouponAdapter(type);
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView(com.tozzais.baselibrary.R.mipmap.empty_view,"暂无优惠券可用哦~","去领取",v -> {
            CouponCenterActivity.launch(mActivity);
        });


    }

    @Override
    public void loadData() {
        super.loadData();
        new Handler().postDelayed(()->{
            List<CouponsBean> list = new ArrayList<>();
            list.add(new CouponsBean(0));
            list.add(new CouponsBean(1));
            list.add(new CouponsBean(2));
            setData(list);
        },500);

    }

    @Override
    public void initListener() {
        super.initListener();


    }

    @OnClick(R.id.tv_add)
    public void onClick() {
        CouponCenterActivity.launch(mActivity);

    }
}
