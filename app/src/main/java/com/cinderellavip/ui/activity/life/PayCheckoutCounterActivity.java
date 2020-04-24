package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cinderellavip.R;
import com.cinderellavip.ui.activity.order.PayResultActivity;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class PayCheckoutCounterActivity extends BaseActivity {


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

    public static void launch(Context from) {
        Intent intent = new Intent(from, PayCheckoutCounterActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("支付收银台");



    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_checkout_counter;
    }



    @OnClick({R.id.ll_pay_ali, R.id.ll_pay_wechat, R.id.ll_pay_balance, R.id.tv_buy})
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
            case R.id.tv_buy:
                tsg("支付成功");
                finish();

                break;
        }
    }

    private String payway = "1";
    private void setPayWay(int way) {
        payway = way + "";
        ivPayWechat.setImageResource(way == 3 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
        ivPayAli.setImageResource(way == 1 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
        ivPayBalance.setImageResource(way == 5 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
    }
}
