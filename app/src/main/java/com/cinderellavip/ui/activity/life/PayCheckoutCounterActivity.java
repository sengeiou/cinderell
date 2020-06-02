package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.PrePayLongOrder;
import com.cinderellavip.bean.eventbus.UpdateLongServiceOrder;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.net.life.LifeCoupon;
import com.cinderellavip.bean.net.life.PayCheckResult;
import com.cinderellavip.bean.net.order.GetPayResult;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.Utils;
import com.cinderellavip.util.pay.PayUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import androidx.annotation.Nullable;
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
    @BindView(R.id.ll_coupon)
    LinearLayout ll_coupon;
    @BindView(R.id.tv_service_coupon)
    TextView tvServiceCoupon;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;

    //请求参数
    public PrePayLongOrder prePayLongOrder;
    //结果
    private PayCheckResult payCheckResult;
    public static void launch(Context from, PrePayLongOrder prePayLongOrder) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, PayCheckoutCounterActivity.class);
        intent.putExtra("prePayLongOrder", prePayLongOrder);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        prePayLongOrder = getIntent().getParcelableExtra("prePayLongOrder");
        if (prePayLongOrder.type == PrePayLongOrder.LONG) {
            ll_coupon.setVisibility(View.VISIBLE);
        }if (prePayLongOrder.type == PrePayLongOrder.PROJECT) {
            ll_coupon.setVisibility(View.GONE);
        }
        setBackTitle("支付收银台");



    }


    @Override
    public void loadData() {
        if (!isLoad) showProress();
        if (prePayLongOrder.type == PrePayLongOrder.LONG) {
            ll_coupon.setVisibility(View.VISIBLE);
            getLongPreOrder();
        }else if (prePayLongOrder.type == PrePayLongOrder.PROJECT) {
            ll_coupon.setVisibility(View.GONE);
            getProjectPreOrder();
        }

    }

    private void getLongPreOrder(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("contracts_id", "" + prePayLongOrder.contracts_id);
        hashMap.put("coupon", "" + prePayLongOrder.coupon);
        new RxHttp<BaseResult<PayCheckResult>>().send(ApiManager.getService().prePayLongOrder(hashMap),
                new Response<BaseResult<PayCheckResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<PayCheckResult> result) {
                        showContent();
                        payCheckResult = result.data;
                        tvOrderMoney.setText(payCheckResult.getPrice()+"元");
                        tvCouponMoney.setText("-"+payCheckResult.getDiscount()+"元");
                        tvPayMoney.setText(payCheckResult.getActual()+"元");

                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    private void getProjectPreOrder(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", "" + prePayLongOrder.project);
        new RxHttp<BaseResult<PayCheckResult>>().send(ApiManager.getService().prePayShortOrder(hashMap),
                new Response<BaseResult<PayCheckResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<PayCheckResult> result) {
                        showContent();
                        payCheckResult = result.data;
                        tvOrderMoney.setText(payCheckResult.getPrice()+"元");
                        tvCouponMoney.setText("-"+payCheckResult.getDiscount()+"元");
                        tvPayMoney.setText(payCheckResult.getActual()+"元");

                    }
                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_checkout_counter;
    }


    @OnClick({R.id.ll_service_coupon, R.id.ll_pay_ali, R.id.ll_pay_wechat, R.id.ll_pay_balance, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_service_coupon:
                ServiceSelectCouponActivity.launch(mActivity,prePayLongOrder.contracts_id);
                break;
            case R.id.ll_pay_ali:
                setPayWay(1);
                break;
            case R.id.ll_pay_wechat:
                setPayWay(2);
                break;
            case R.id.ll_pay_balance:
                setPayWay(0);
                break;
            case R.id.tv_buy:
                if (prePayLongOrder.type == PrePayLongOrder.LONG) {
                    pay();
                }else if (prePayLongOrder.type == PrePayLongOrder.PROJECT) {
                     projectPay();
                }

                break;
        }
    }

    private String payway = "1";
    private void setPayWay(int way) {
        payway = way + "";
        ivPayWechat.setImageResource(way == 2 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
        ivPayAli.setImageResource(way == 1 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
        ivPayBalance.setImageResource(way == 0 ? R.mipmap.service_agreement_select : R.mipmap.service_agreement_default);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_coupon && resultCode == RESULT_OK) {
            if (data != null){
                LifeCoupon couponsBean = data.getParcelableExtra("couponsBean");
                tvServiceCoupon.setText("满"+couponsBean.getFull()+"元减"+couponsBean.getLess());
                prePayLongOrder.coupon = couponsBean.id+"";
                loadData();
            } else {
                tvServiceCoupon.setText("");
                tvServiceCoupon.setHint("请选择");
                prePayLongOrder.coupon = "";
                loadData();
            }
        }
    }

    private void pay(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("contracts_id", prePayLongOrder.contracts_id);
        hashMap.put("coupon", prePayLongOrder.coupon);
        hashMap.put("type", payway);
        new RxHttp<BaseResult<GetPayResult>>().send(ApiManager.getService().lifeOrderPay(hashMap),
                new Response<BaseResult<GetPayResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GetPayResult> result) {
                        PayUtil.payLifeOrder(mActivity,payway,result.data, isSuccess -> {
                            if (isSuccess){
                                tsg("支付成功");
                                EventBus.getDefault().post(new UpdateLongServiceOrder());
                                finish();
                            }else {
                                tsg("支付失败");
                            }

                        });
                    }
                });
    }

    private void projectPay(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", prePayLongOrder.project);
        hashMap.put("type", payway);
        new RxHttp<BaseResult<GetPayResult>>().send(ApiManager.getService().projectOrderPay(hashMap),
                new Response<BaseResult<GetPayResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<GetPayResult> result) {
                        PayUtil.payLifeOrder(mActivity,payway,result.data, isSuccess -> {
                            if (isSuccess){
                                SingleServiceOrderListActivity.launch(mActivity,SingleServiceOrderListActivity.ALL);
                                EventBus.getDefault().post(new UpdateShortServiceOrder());
                                finish();
                            }else {
                                tsg("支付失败");
                            }

                        });
                    }
                });
    }
}
