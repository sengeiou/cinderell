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
import com.cinderellavip.bean.net.life.LongOrderDetailResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.toast.ToastCommom;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class LongServiceOrderDetailActivity extends BaseActivity {


    @BindView(R.id.ll_progress)
    LinearLayout ll_progress;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.tv_service_number)
    TextView tvServiceNumber;
    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.tv_service_time)
    TextView tvServiceTime;
    @BindView(R.id.tv_claim)
    TextView tvClaim;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_look)
    TextView tvLook;
    @BindView(R.id.tv_cost_explain)
    TextView tvCostExplain;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;
    @BindView(R.id.tv_money_coupon)
    TextView tvMoneyCoupon;

    //订单id
    private int type;

    public static void launch(Context from, int type) {
        Intent intent = new Intent(from, LongServiceOrderDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        type = getIntent().getIntExtra("type", 0);
        setBackTitle("订单详情");
        setRightText("联系客服");

    }


    @Override
    public void loadData() {
        if (!isLoad) showProress();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", "" + type);
        new RxHttp<BaseResult<LongOrderDetailResult>>().send(ApiManager.getService().longOrderDetail(hashMap),
                new Response<BaseResult<LongOrderDetailResult>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<LongOrderDetailResult> result) {
                        showContent();
                        longOrderDetailResult = result.data;
                        setData();
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    private void setData() {
        tvServiceNumber.setText("服务编号："+longOrderDetailResult.code);
        tvServiceType.setText("服务类型："+longOrderDetailResult.service_name+"-"+longOrderDetailResult.project_title);
        tvServiceTime.setText("服务周期："+longOrderDetailResult.cycle+"个月");
        tvClaim.setText(""+longOrderDetailResult.claim);
        tvAddress.setText("服务地址："+longOrderDetailResult.address.address);
        tvPhone.setText("联系电话："+longOrderDetailResult.address.phone);
        tvUsername.setText("联系人："+longOrderDetailResult.address.name);
        tvId.setText("合同编号："+longOrderDetailResult.cont_code);
        tvTime.setText("合同起止时间："+longOrderDetailResult.cont_startTime+""+longOrderDetailResult.cont_endTime);
        tvMoney.setText(longOrderDetailResult.getPrice()+"元");
        tvMoneyPay.setText(longOrderDetailResult.getActual()+"元");
        tvMoneyCoupon.setText(longOrderDetailResult.getDiscount()+"元");
        setStatus();

    }

    LongOrderDetailResult longOrderDetailResult;


    @Override
    public int getLayoutId() {
        return R.layout.activity_longservice_order_detail;
    }


    @OnClick({R.id.tv_btn1, R.id.tv_btn2, R.id.tv_look, R.id.tv_cost_explain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cost_explain:
//                ServiceOrderCommentActivity.launch(mActivity,type+"");
//                CenterDialogUtil.showCostExplain(mActivity, s -> {
//                });
                break;
            case R.id.tv_look:
                if (longOrderDetailResult != null){
                    AgreementWebViewActivity.launch(mActivity,longOrderDetailResult.url);
                }
//                WebViewActivity.launch(mActivity, "服务签约电子合同", "https://www.baidu.com");
                break;
            case R.id.tv_btn1:
                if (longOrderDetailResult.type == 1) {
                    CenterDialogUtil.showServiceOrder(mActivity, "确认提示", "您确定要取消该订单吗？\n取消后不可撤回"
                            , "取消", "确定", s -> {
                                cancel(type+"");
                            });
                }
                break;
            case R.id.tv_btn2:
                if (longOrderDetailResult.type == 1) {
                    CenterDialogUtil.showServiceOrder(mActivity, "确认提示", "请仔细核对订单信息\n确认后不可撤回"
                            , "取消", "确定", s -> {
                                confirm(type+"");
                            });
                } else if (longOrderDetailResult.type == 2) {
                    //去支付
                    PrePayLongOrder prePayLongOrder = new PrePayLongOrder();
                    prePayLongOrder.contracts_id = longOrderDetailResult.cont_id+"";
                    prePayLongOrder.coupon = "";
                    prePayLongOrder.type = PrePayLongOrder.LONG;
                    PayCheckoutCounterActivity.launch(mActivity,prePayLongOrder);
                } else if (longOrderDetailResult.type == 5) {
                    //评价
                    ServiceOrderCommentActivity.launch(mActivity,type+"");
                }
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            DialogUtil.showCallPhoneDialog(mActivity,3);
        });
    }

    public void cancel(String contracts_id) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", ""+contracts_id);
        new RxHttp<BaseResult>().send(ApiManager.getService().cancelLongOrder(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        loadData();
                        ToastCommom.createToastConfig().ToastShow(mActivity,"操作成功");
                        EventBus.getDefault().post(new UpdateLongServiceOrder());
                    }
                });
    }
    public void confirm(String contracts_id) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("contracts_id", ""+contracts_id);
        new RxHttp<BaseResult>().send(ApiManager.getService().longOrderConfirm(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        loadData();
                        ToastCommom.createToastConfig().ToastShow(mActivity,"操作成功");
                        EventBus.getDefault().post(new UpdateLongServiceOrder());
                    }
                });


    }

    @BindView(R.id.iv_progress1)
    ImageView ivProgress1;
    @BindView(R.id.tv_progress1)
    TextView tvProgress1;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.iv_progress2)
    ImageView ivProgress2;
    @BindView(R.id.tv_progress2)
    TextView tvProgress2;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.iv_progress3)
    ImageView ivProgress3;
    @BindView(R.id.tv_progress3)
    TextView tvProgress3;
    @BindView(R.id.view5)
    View view5;
    @BindView(R.id.view6)
    View view6;
    @BindView(R.id.iv_progress4)
    ImageView ivProgress4;
    @BindView(R.id.tv_progress4)
    TextView tvProgress4;

    private void setStatus() {
        setStatus(longOrderDetailResult.type);
        if (longOrderDetailResult.type == 1) {
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            tvBtn1.setText("取消");
            tvBtn2.setText("确定");
        } else if (longOrderDetailResult.type == 2) {
            tvBtn1.setText("取消");
            tvBtn2.setText("支付");
        } else if (longOrderDetailResult.type == 3 ||longOrderDetailResult.type == 4||longOrderDetailResult.type == 7) {
            llBottom.setVisibility(View.GONE);
        } else if (longOrderDetailResult.type == 5 || type == 6) {
            if (type == 6) {
                llBottom.setVisibility(View.GONE);
            } else {
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setText("评价");
            }
        }
    }

    private void setStatus(int type) {
        ll_progress.setVisibility(View.VISIBLE);
        if (type == 1){
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center);
            view3.setBackgroundColor(getColor(R.color.gray_progress));
            view4.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center);
            view5.setBackgroundColor(getColor(R.color.gray_progress));
            view6.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress4.setImageResource(R.mipmap.progress_right);
            tvProgress1.setTextColor(getResources().getColor(R.color.yellow_progress));

        }else if (type == 2 || type == 3){
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            view4.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center);
            view5.setBackgroundColor(getColor(R.color.gray_progress));
            view6.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress4.setImageResource(R.mipmap.progress_right);
            tvProgress1.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress2.setTextColor(getResources().getColor(R.color.yellow_progress));
        }else if (type == 4){
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            view4.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center_select);
            view5.setBackgroundColor(getColor(R.color.yellow_progress));
            view6.setBackgroundColor(getColor(R.color.gray_progress));
            ivProgress4.setImageResource(R.mipmap.progress_right);
            tvProgress1.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress2.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress3.setTextColor(getResources().getColor(R.color.yellow_progress));
        }else if (type == 5 || type == 6){
            ivProgress1.setImageResource(R.mipmap.progress_left_select);
            view1.setBackgroundColor(getColor(R.color.yellow_progress));
            view2.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress2.setImageResource(R.mipmap.progress_center_select);
            view3.setBackgroundColor(getColor(R.color.yellow_progress));
            view4.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress3.setImageResource(R.mipmap.progress_center_select);
            view5.setBackgroundColor(getColor(R.color.yellow_progress));
            view6.setBackgroundColor(getColor(R.color.yellow_progress));
            ivProgress4.setImageResource(R.mipmap.progress_right_select);
            tvProgress1.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress2.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress3.setTextColor(getResources().getColor(R.color.yellow_progress));
            tvProgress4.setTextColor(getResources().getColor(R.color.yellow_progress));
        }else {
            ll_progress.setVisibility(View.GONE);
        }

    }



}
