package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.CouponCenterAdapter;
import com.cinderellavip.bean.ListCoupons;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.MineCouponActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

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
        getCouponList();

    }
    private void getCouponList(){
        new RxHttp<BaseResult<ListCoupons<CouponsBean>>>().send(ApiManager.getService().couponsCenter(),
                new Response<BaseResult<ListCoupons<CouponsBean>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListCoupons<CouponsBean>> result) {
                        setData(result.data.coupons);
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
        super.initListener();

    }

    @OnClick(R.id.tv_add)
    public void onClick() {
        MineCouponActivity.launch(mActivity);
    }


}
