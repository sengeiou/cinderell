package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ServiceListAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.life.BuyLongServiceActivity;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class ServiceListFragment extends BaseListFragment<String> implements View.OnClickListener {


    @Override
    public int setLayout() {
        return R.layout.fragment_service_list;
    }


    public static ServiceListFragment newInstance() {
        ServiceListFragment cartFragment = new ServiceListFragment();
        return cartFragment;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ServiceListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setEmptyView("暂无数据");

        View view = View.inflate(mActivity, R.layout.header_service_list, null);
        iv_buy_long_service = view.findViewById(R.id.iv_buy_long_service);
        iv_buy_long_service.setOnClickListener(this);
        tv_receive_coupon = view.findViewById(R.id.tv_receive_coupon);
        tv_receive_coupon.setOnClickListener(this);
        mAdapter.addHeaderView(view);


    }
    private ImageView iv_buy_long_service;
    private TextView tv_receive_coupon;

    @Override
    public void loadData() {
        super.loadData();


        setData(DataUtil.getData(2));

    }


    @OnClick(R.id.tv_service)
    public void onClick() {
        DialogUtil.showCallPhoneDialog(mActivity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_buy_long_service:
                BuyLongServiceActivity.launch(getContext());
                break;
            case R.id.tv_receive_coupon:
                List<CouponsBean> list = new ArrayList<>();
                list.add(new CouponsBean());
                list.add(new CouponsBean());
                list.add(new CouponsBean());
                DialogUtil.showServiceCouponDialog(mActivity, list);
                break;
        }

    }
}
