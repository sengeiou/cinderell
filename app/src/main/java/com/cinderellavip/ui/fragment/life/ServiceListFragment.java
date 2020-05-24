package com.cinderellavip.ui.fragment.life;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.ServiceListAdapter;
import com.cinderellavip.bean.ListData;
import com.cinderellavip.bean.net.life.CategoryResult;
import com.cinderellavip.bean.net.life.CategoryService;
import com.cinderellavip.bean.net.life.LifeCoupon;
import com.cinderellavip.bean.net.life.LiftHomeCategory;
import com.cinderellavip.bean.net.life.LiftHomeServiceItem;
import com.cinderellavip.bean.net.life.ListServiceLocalItem;
import com.cinderellavip.bean.net.life.NewPersonCoupon;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.activity.life.BuyLongServiceActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.OnClick;


public class ServiceListFragment extends BaseListFragment<ListServiceLocalItem> implements View.OnClickListener {


    @Override
    public int setLayout() {
        return R.layout.fragment_service_list;
    }


    public static ServiceListFragment newInstance(LiftHomeCategory service) {
        ServiceListFragment cartFragment = new ServiceListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("service",service);
        cartFragment.setArguments(bundle);
        return cartFragment;

    }

    private LiftHomeCategory service;



    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        service = getArguments().getParcelable("service");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new ServiceListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        View view = View.inflate(mActivity, R.layout.header_service_list, null);
        iv_banner = view.findViewById(R.id.iv_banner);
        ll_new_person = view.findViewById(R.id.ll_new_person);
        tv_coupon_name = view.findViewById(R.id.tv_coupon_name);
        iv_buy_long_service = view.findViewById(R.id.iv_buy_long_service);
        iv_buy_long_service.setOnClickListener(this);
        tv_receive_coupon = view.findViewById(R.id.tv_receive_coupon);
        tv_receive_coupon.setOnClickListener(this);
        mAdapter.addHeaderView(view);


    }
    private ImageView iv_banner;
    private LinearLayout ll_new_person;
    private TextView tv_coupon_name;
    private ImageView iv_buy_long_service;
    private TextView tv_receive_coupon;

    @Override
    public void loadData() {
        super.loadData();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("city", CinderellApplication.name);
        hashMap.put("service",service.one+"");
        hashMap.put("four",service.three+"");
        new RxHttp<BaseResult<CategoryResult>>().send(ApiManager.getService().life_category(hashMap),
                new Response<BaseResult<CategoryResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CategoryResult> result) {
                        CategoryResult data = result.data;
                        CategoryService service = data.service;
                        if (service == null && TextUtils.isEmpty(service.topimg)){
                            iv_banner.setVisibility(View.GONE);
                        }else {
                            iv_banner.setVisibility(View.VISIBLE);
                            ImageUtil.loadNet(mActivity,iv_banner,service.topimg);
                        }
                        NewPersonCoupon coupon = data.coupon;
                        if (coupon != null && !TextUtils.isEmpty(coupon.title)){
                            ll_new_person.setVisibility(View.VISIBLE);
                            tv_coupon_name.setText(coupon.title);
                        }else {
                            ll_new_person.setVisibility(View.GONE);
                        }
                        LiftHomeServiceItem lo_ng = data.longServiceItem;
                        if (lo_ng != null && !TextUtils.isEmpty(lo_ng.thumb_nail)){
                            iv_buy_long_service.setVisibility(View.VISIBLE);
                            ImageUtil.loadNet(mActivity,iv_buy_long_service,lo_ng.thumb_nail);
                        }else {
                            iv_buy_long_service.setVisibility(View.GONE);

                        }

                        List<ListServiceLocalItem> list = new ArrayList<>();
                        list.add(new ListServiceLocalItem(data.project,"服务项目"));
                        list.add(new ListServiceLocalItem(data.pack_age,"服务套餐"));
                        setData(true,list);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });




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
                getCoupons();
                break;
        }

    }

    private void getCoupons(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("service", ""+service.one);
        new RxHttp<BaseResult<ListData<LifeCoupon>>>().send(ApiManager.getService().categoryCoupon(hashMap),
                new Response<BaseResult<ListData<LifeCoupon>>>(getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListData<LifeCoupon>> result) {
                        DialogUtil.showServiceCouponDialog(mActivity, result.data.data);
                    }
                });
    }
}
