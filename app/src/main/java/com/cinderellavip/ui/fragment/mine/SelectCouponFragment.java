package com.cinderellavip.ui.fragment.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.SeleteCoupondapter;
import com.cinderellavip.bean.ListCoupons;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.local.SelectCouponsBean;
import com.cinderellavip.bean.net.order.RequestSelectCoupons;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class SelectCouponFragment extends BaseListFragment<SelectCouponsBean> {


    public static SelectCouponFragment newInstance(RequestSelectCoupons requestSelectCoupons ) {
        SelectCouponFragment cartFragment = new SelectCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("requestSelectCoupons",requestSelectCoupons);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_selete_coupon;
    }


    RequestSelectCoupons requestSelectCoupons;
    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        assert getArguments() != null;
        requestSelectCoupons = getArguments().getParcelable("requestSelectCoupons");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SeleteCoupondapter();
        mRecyclerView.setAdapter(mAdapter);

        setEmptyView("暂无优惠券可使用");


    }

    @Override
    public void loadData() {
        super.loadData();
        getCouponList();


    }
    private void getCouponList(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("store_id", requestSelectCoupons.store_id);
        hashMap.put("goods_amount", requestSelectCoupons.goods_amount);
        new RxHttp<BaseResult<ListCoupons<SelectCouponsBean>>>().send(ApiManager.getService().couponsSettlement(hashMap),
                new Response<BaseResult<ListCoupons<SelectCouponsBean>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListCoupons<SelectCouponsBean>> result) {
                        List<SelectCouponsBean> coupons = result.data.coupons;
                        for (SelectCouponsBean selectCouponsBean:coupons){
                            if (selectCouponsBean.id == requestSelectCoupons.coupon.id){
                                selectCouponsBean.isCheck = true;
                                break;
                            }
                        }
                        setData(coupons);
                    }
                });

    }

    @Override
    public void initListener() {
        super.initListener();

        mAdapter.setOnItemClickListener(((baseQuickAdapter, view, position) -> {

            List<SelectCouponsBean> data = mAdapter.getData();
            SelectCouponsBean couponsBean = data.get(position);
                Intent intent = new Intent();
                intent.putExtra("couponsBean", couponsBean);
                mActivity.setResult(Activity.RESULT_OK, intent);
                mActivity.finish();

        }));

    }

    @OnClick(R.id.tv_btn_nouse)
    public void onClick() {
        SelectCouponsBean couponsBean = new SelectCouponsBean();
        couponsBean.id = requestSelectCoupons.coupon.id;
        Intent intent = new Intent();
        intent.putExtra("couponsBean", couponsBean);
        mActivity.setResult(Activity.RESULT_OK, intent);
        mActivity.finish();

    }
}
