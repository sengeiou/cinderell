package com.cinderellavip.ui.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.HomeGoodsAdapter;
import com.cinderellavip.bean.eventbus.OrderPaySuccess;
import com.cinderellavip.bean.eventbus.ReceiveOrder;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.order.CreateOrderBean;
import com.cinderellavip.bean.net.order.OrderGoodsInfo;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.MineGroupActivity;
import com.cinderellavip.ui.activity.mine.MineOrderActivity;
import com.cinderellavip.ui.fragment.mine.OrderFragment;
import com.cinderellavip.util.Utils;
import com.cinderellavip.weight.GirdSpace;
import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseListActivity;
import com.tozzais.baselibrary.util.DpUtil;

import org.greenrobot.eventbus.EventBus;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 *
 */
public class PayResultActivity extends BaseListActivity implements View.OnClickListener {



    private TextView tv_pay_result;
    private TextView tv_pay_money;
    private TextView tv_look_order;
    private TextView tv_go_home;
    private ImageView iv_code;


    private CreateOrderBean createOrderBean;
    private boolean success;

    public static void launch(Activity activity, CreateOrderBean createOrderBean, boolean success) {
        if (!Utils.isFastClick()){
            return;
        }
        EventBus.getDefault().post(new OrderPaySuccess());
        Intent intent = new Intent(activity, PayResultActivity.class);
        intent.putExtra("createOrderBean", new Gson().toJson(createOrderBean));
        intent.putExtra("success", success);
        activity.startActivity(intent);
        activity.finish();
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_recycleview;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackTitle("支付结果");
        Intent intent = getIntent();
        createOrderBean = new Gson().fromJson(intent.getStringExtra("createOrderBean"), CreateOrderBean.class);
        success = intent.getBooleanExtra("success",false);



        mAdapter = new HomeGoodsAdapter();
        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 2);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        GirdSpace girdSpace = new GirdSpace(DpUtil.dip2px(mActivity, 8),2,1);
        mRecyclerView.addItemDecoration(girdSpace);
        mRecyclerView.setAdapter(mAdapter);

        View v = View.inflate(mContext, R.layout.header_pay_result, null);
        tv_pay_result = v.findViewById(R.id.tv_pay_result);
        tv_pay_money = v.findViewById(R.id.tv_pay_money);

        tv_look_order = v.findViewById(R.id.tv_look_order);
        tv_go_home = v.findViewById(R.id.tv_go_home);
        iv_code = v.findViewById(R.id.iv_code);
        tv_look_order.setOnClickListener(this);
        tv_go_home.setOnClickListener(this);
        mAdapter.addHeaderView(v);

        tv_pay_money.setText("实付￥"+createOrderBean.pay_amount);
//        if (success){
//            tv_pay_result.setText("支付成功");
//            Drawable drawableLeft = getResources().getDrawable(
//                    R.mipmap.zfcgn);
//            tv_pay_result.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                    null, null, null);
//        }else {
//            tv_pay_result.setText("支付失败");
//            Drawable drawableLeft = getResources().getDrawable(
//                    R.mipmap.zfsb);
//            tv_pay_result.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
//                    null, null, null);
//        }


    }


    @Override
    public void loadData() {

        getLoveData();

    }


    //得到猜你喜欢的数据
    private void getLoveData() {
        new RxHttp<BaseResult<ListResult<HomeGoods>>>().send(ApiManager.getService().getLicks(),
                new Response<BaseResult<ListResult<HomeGoods>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ListResult<HomeGoods>> result) {
                        setData(result.data.list);
                    }
                });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_look_order:
                if (createOrderBean.type == CreateOrderBean.PRODUCT || createOrderBean.type == CreateOrderBean.CART){
                    MineOrderActivity.launch(mActivity, OrderFragment.ALL);
                }else {
                    MineGroupActivity.launch(mActivity);
                }

//                OrderDetailActivity.launch(mActivity,createOrderBean.order_id);
                finish();
                break;
            case R.id.tv_go_home:
                MainActivity.launch(mActivity,MainActivity.SHOP);
                break;
        }
    }

    @Override
    public void back() {
        finish();
    }
    @Override
    public void onBackPressed() {
        back();
    }

}