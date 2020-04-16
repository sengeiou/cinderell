package com.cinderellavip.ui.activity.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class SelectPayWayActivity extends BaseActivity {


    @BindView(R.id.iv_pay_ali)
    ImageView ivPayAli;
    @BindView(R.id.iv_pay_wechat)
    ImageView ivPayWechat;
    @BindView(R.id.iv_pay_balance)
    ImageView ivPayBalance;
    @BindView(R.id.ll_pay_ali)
    LinearLayout llPayAli;
    @BindView(R.id.ll_pay_wechat)
    LinearLayout llPayWechat;
    @BindView(R.id.ll_pay_balance)
    LinearLayout llPayBalance;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_balance)
    TextView tv_balance;


    private String payMoney;
    private int order_id;

    public static void launch(Context activity, int order_id, String payMoney) {
        Intent intent = new Intent(activity, SelectPayWayActivity.class);
        intent.putExtra("payMoney", payMoney);
        intent.putExtra("order_id", order_id);
        activity.startActivity(intent);
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_way;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("支付方式");
        llPayBalance.setVisibility(View.VISIBLE);

//        payMoney = getIntent().getStringExtra("payMoney");
//        order_id = getIntent().getIntExtra("order_id", 0);
//        tv_money.setText("HK$ " + payMoney);

    }


    @Override
    public void loadData() {


    }


    @OnClick({R.id.ll_pay_ali, R.id.ll_pay_wechat, R.id.ll_pay_balance, R.id.tv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pay_ali:
                setPayWay(1);
                break;
            case R.id.ll_pay_wechat:
                setPayWay(3);
                break;
            case R.id.ll_pay_balance:
                setPayWay(5);
                break;
            case R.id.tv_add:
                PayResultActivity.launch(mActivity,0,true);


                break;
        }
    }

    private String payway = "1";

    private void setPayWay(int way) {
        payway = way + "";
        ivPayWechat.setImageResource(way == 3 ? R.mipmap.gwcxz : R.mipmap.gwcmx);
        ivPayAli.setImageResource(way == 1 ? R.mipmap.gwcxz : R.mipmap.gwcmx);
        ivPayBalance.setImageResource(way == 5 ? R.mipmap.gwcxz : R.mipmap.gwcmx);
    }






}