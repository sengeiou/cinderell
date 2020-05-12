package com.cinderellavip.ui.fragment.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.MineCouponAdapter;
import com.cinderellavip.bean.local.MineCouponsBean;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.CouponCenterActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListFragment;

import java.util.TreeMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;


public class MineCouponFragment extends BaseListFragment<MineCouponsBean> {

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

        setEmptyView("暂无优惠券可用哦~");


    }

    @Override
    public void loadData() {
        super.loadData();
        getCouponList();


    }
    private void getCouponList(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("status", (type+1)+"");
        hashMap.put("page", page+"");
        hashMap.put("limit", PageSize+"");
        new RxHttp<BaseResult<ListResult<MineCouponsBean>>>().send(ApiManager.getService().getMineCoupons(hashMap),
                new Response<BaseResult<ListResult<MineCouponsBean>>>(isLoad,getContext()) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<MineCouponsBean>> result) {
                        setData(result.data.list);
                    }
                });

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
