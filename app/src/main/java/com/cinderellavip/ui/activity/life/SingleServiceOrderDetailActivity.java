package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.PrePayLongOrder;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.net.life.ShortOrderItem;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SingleServiceOrderDetailActivity extends BaseActivity {


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
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_money_coupon)
    TextView tvMoneyCoupon;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;

    //订单id
    private int type;

    public static void launch(Context from, int type) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, SingleServiceOrderDetailActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        setBackTitle("订单详情");
        setRightText("联系客服");

    }

    ShortOrderItem shortOrderItem;

    @Override
    public void loadData() {
        if (!isLoad) showProress();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", "" + type);
        new RxHttp<BaseResult<ShortOrderItem>>().send(ApiManager.getService().shortOrderDetail(hashMap),
                new Response<BaseResult<ShortOrderItem>>(isLoad, mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ShortOrderItem> result) {
                        showContent();
                        shortOrderItem = result.data;
                        setData();
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    private void setData() {
        tvServiceNumber.setText("服务编号："+ shortOrderItem.code);
        tvServiceType.setText("服务类型："+ shortOrderItem.sortname+"-"+ shortOrderItem.title);
        tvServiceTime.setText("服务时间："+ shortOrderItem.starttime);
        tvAddress.setText("服务地址："+ shortOrderItem.address.address+ shortOrderItem.address.doorplate);
        tvPhone.setText("联系电话："+ shortOrderItem.address.phone);
        tvUsername.setText("联系人："+ shortOrderItem.address.name);
        tvMoney.setText(shortOrderItem.getPrice()+"元");
        tvMoneyCoupon.setText(shortOrderItem.getDiscount()+"元");
        tvMoneyPay.setText(shortOrderItem.getActual()+"元");
        setStatus();

    }

    private void setStatus() {
        if (shortOrderItem.pay == 0) {
            llBottom.setVisibility(View.VISIBLE);
            tvBtn1.setVisibility(View.VISIBLE);
            tvBtn2.setVisibility(View.VISIBLE);
            tvBtn1.setText("取消");
            tvBtn2.setText("支付");
        } else {
            if (shortOrderItem.status == 2){
                llBottom.setVisibility(View.VISIBLE);
                tvBtn1.setVisibility(View.GONE);
                tvBtn2.setVisibility(View.VISIBLE);
                tvBtn2.setText("评价");
            }else {
                llBottom.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_order_detail;
    }


    @OnClick({R.id.tv_btn1, R.id.tv_btn2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn1:
                //取消订单
                CenterDialogUtil.showServiceOrder(mActivity, "确认提示", "您确定要取消订单吗？\n取消后不可撤回"
                        , "取消", "确定", s -> {
                                cancel(shortOrderItem.id+"");
                        });
                break;
            case R.id.tv_btn2:
                if (shortOrderItem.pay == 0) {
                    //去支付
                    PrePayLongOrder prePayLongOrder = new PrePayLongOrder();
                    prePayLongOrder.project = type+"";
                    prePayLongOrder.type = PrePayLongOrder.PROJECT;
                    PayCheckoutCounterActivity.launch(mActivity,prePayLongOrder);
                } else if (shortOrderItem.status == 2) {
                    //去评价
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

    /**
     */
    public void cancel(String order) {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order", ""+order);
        new RxHttp<BaseResult>().send(ApiManager.getService().cancelShortOrder(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("已取消");
                        EventBus.getDefault().post(new UpdateShortServiceOrder());
                        loadData();
                    }
                });
    }
}
