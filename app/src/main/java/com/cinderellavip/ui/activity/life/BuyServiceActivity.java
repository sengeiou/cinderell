package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.PrePayLongOrder;
import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.bean.net.life.LifeCoupon;
import com.cinderellavip.bean.net.life.ShortDate;
import com.cinderellavip.bean.net.life.ShortPreOrderResult;
import com.cinderellavip.bean.net.life.ShortTime;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BuyServiceActivity extends BaseActivity {


    @BindView(R.id.tv_address_tip)
    TextView tvAddressTip;
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_name3)
    TextView tvName3;
    @BindView(R.id.ll_address_info)
    LinearLayout llAddressInfo;
    @BindView(R.id.ll_select_address)
    LinearLayout llSelectAddress;
    @BindView(R.id.tv_default_line)
    View tvDefaultLine;
    @BindView(R.id.tv_service_time)
    TextView tvServiceTime;
    @BindView(R.id.ll_service_time)
    LinearLayout llServiceTime;
    @BindView(R.id.tv_service_coupon)
    TextView tvServiceCoupon;
    @BindView(R.id.ll_service_coupon)
    LinearLayout llServiceCoupon;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.iv_agreement)
    ImageView ivAgreement;
    @BindView(R.id.tv_register_agreement)
    TextView tvRegisterAgreement;
    @BindView(R.id.tv_buy)
    TextView tvBuy;

    //项目id
    private int project;
    private String city;
    public static void launch(Context from,int project,String city) {
        Intent intent = new Intent(from, BuyServiceActivity.class);
        intent.putExtra("project",project);
        intent.putExtra("city",city);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        setBackTitle("购买服务");
        project = getIntent().getIntExtra("project",-1);
        city = getIntent().getStringExtra("city");
        prePayLongOrder = new PrePayLongOrder(""+project,city);

    }
    //请求参数封装
    private PrePayLongOrder prePayLongOrder;

    @Override
    public void loadData() {
        TreeMap<String,String> map = new TreeMap<>();
        map.put("city", city +"");
        map.put("project",project+"");
        map.put("address",""+prePayLongOrder.address);
        map.put("coupon",""+prePayLongOrder.coupon);
        map.put("starttime",""+prePayLongOrder.starttime);
        map.put("endtime",""+prePayLongOrder.endtime);
        new RxHttp<BaseResult<ShortPreOrderResult>>().send(ApiManager.getService().shortPreOrder(map),
                new Response<BaseResult<ShortPreOrderResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ShortPreOrderResult> result) {
                        ShortPreOrderResult shortPreOrderResult = result.data;
                        tvOrderMoney.setText(shortPreOrderResult.getPrice()+"元");
                        tvCouponMoney.setText("-"+shortPreOrderResult.getDiscount()+"元");
                        tvPayMoney.setText(shortPreOrderResult.getActual()+"元");
                        tv_money.setText(shortPreOrderResult.getActual()+"元");

                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_one_service;
    }


    @OnClick({R.id.ll_select_address, R.id.ll_service_time, R.id.ll_service_coupon,
            R.id.iv_agreement, R.id.tv_register_agreement, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_address:
                SelectServiceAddressActivity.launch(mActivity, SelectServiceAddressActivity.SELECT);
                break;
            case R.id.ll_service_time:
//                TimeUtil3s.getInstance().showSelectDialog(mContext,(year, month, day) -> {
//                    tvServiceTime.setText("1月6号 今天 18:00");
//                });
                SelectServiceTimeActivity.launch(mActivity);
                break;
            case R.id.ll_service_coupon:
                ServiceSelectCouponActivity.launch(mActivity,""+project,ServiceSelectCouponActivity.PROJECT);
//                ServiceSelectCouponActivity.launch(mActivity);
                break;
            case R.id.iv_agreement:
                isSelect = !isSelect;
                ivAgreement.setImageResource(isSelect ?R.mipmap.service_agreement_select:R.mipmap.service_agreement_default);
                break;
            case R.id.tv_register_agreement:
                AgreementWebViewActivity.launch(mActivity, Constant.H5_SERVICE);
                break;
            case R.id.tv_buy:
                buy();
                break;
        }
    }

    private void buy(){
        if (netCityBean == null){
            tsg("请选择服务地址");
            return;
        }
        if (TextUtils.isEmpty(tvServiceTime.getText().toString().trim())){
            tsg("请选择服务时间");
            return;
        }if (!isSelect){
            tsg("请先同意灰姑娘服务协议");
            return;
        }
        prePayLongOrder.type = PrePayLongOrder.PROJECT;
        prePayLongOrder.project = project+"";
        prePayLongOrder.address = netCityBean.id+"";
        prePayLongOrder.coupon = prePayLongOrder.coupon+"";
        prePayLongOrder.starttime = prePayLongOrder.starttime+"";
        prePayLongOrder.endtime = prePayLongOrder.endtime+"";
        prePayLongOrder.city = prePayLongOrder.city+"";
        createOrder();



    }

    private void createOrder(){
        TreeMap<String,String> map = new TreeMap<>();
        map.put("city", prePayLongOrder.city +"");
        map.put("project",project+"");
        map.put("address",""+prePayLongOrder.address);
        map.put("coupon",""+prePayLongOrder.coupon);
        map.put("starttime",""+prePayLongOrder.starttime);
        map.put("endtime",""+prePayLongOrder.endtime);
        new RxHttp<BaseResult<Integer>>().send(ApiManager.getService().createShortPreOrder(map),
                new Response<BaseResult<Integer>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<Integer> result) {
                        prePayLongOrder.project = result.data+"";
                        LogUtil.e(prePayLongOrder.toString());
                        PayCheckoutCounterActivity.launch(mActivity,prePayLongOrder);
                        finish();
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_address && resultCode == RESULT_OK) {
            netCityBean  = data.getParcelableExtra("netCityBean");
            setAddress(netCityBean);
            prePayLongOrder.address = netCityBean.id+"";
            loadData();
        }else if (requestCode == RequestCode.request_service_time && resultCode == RESULT_OK) {
            ShortTime time = data.getParcelableExtra("time");
            ShortDate shortDate = data.getParcelableExtra("shortDate");
            prePayLongOrder.starttime = shortDate.nian+"-"+shortDate.yue+"-"+shortDate.ri+" "+time.start;
            prePayLongOrder.endtime = shortDate.nian+"-"+shortDate.yue+"-"+shortDate.ri+" "+time.end;
            tvServiceTime.setText(prePayLongOrder.starttime);
        }if (requestCode == RequestCode.request_service_coupon && resultCode == RESULT_OK) {
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

    LifeCityBean netCityBean;
    private void setAddress(LifeCityBean address) {
        netCityBean = address;
        if (address == null ) {
            tvAddressTip.setVisibility(View.VISIBLE);
            llAddressInfo.setVisibility(View.GONE);
        } else {
            tvAddressTip.setVisibility(View.GONE);
            llAddressInfo.setVisibility(View.VISIBLE);
            tvName1.setText(netCityBean.title);
            tvName2.setText(netCityBean.address+netCityBean.doorplate);
            tvName3.setText(netCityBean.name+netCityBean.phone);
        }
    }
    private boolean isSelect = false;
}
