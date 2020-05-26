package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.recycleview.EnsureOrderAdapter;
import com.cinderellavip.bean.eventbus.UpdateCart;
import com.cinderellavip.bean.local.RequestSettlePara;
import com.cinderellavip.bean.local.SelectCouponsBean;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.order.CreateOrderBean;
import com.cinderellavip.bean.net.order.OrderSettleResult;
import com.cinderellavip.bean.net.order.OrderSettleShopBean;
import com.cinderellavip.bean.request.OrderRemark;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.cinderellavip.ui.activity.order.SelectPayWayActivity;
import com.cinderellavip.util.CouponsStringUtil;
import com.google.gson.Gson;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class EnsureOrderActivity extends BaseActivity {


    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;


    //收货地址有关
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.ll_hava_address)
    LinearLayout llHavaAddress;
    @BindView(R.id.ll_no_address)
    LinearLayout llNoAddress;
    @BindView(R.id.ll_selete_address)
    LinearLayout llSeleteAddress;


    @BindView(R.id.tv_tax_total)
    TextView tvTaxTotal;
    @BindView(R.id.tv_coupon_total)
    TextView tvCouponTotal;
    @BindView(R.id.tv_goods_total)
    TextView tvGoodsTotal;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_pay_monet)
    TextView tvPayMonet;


    private RequestSettlePara requestSettlePara;

    public static void launch(Context activity, RequestSettlePara requestSettlePara) {
        Intent intent = new Intent(activity, EnsureOrderActivity.class);
        intent.putExtra("requestSettlePara", requestSettlePara);
        activity.startActivity(intent);
    }


    private EnsureOrderAdapter ensureOrderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ensure_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("确认订单");
        requestSettlePara = getIntent().getParcelableExtra("requestSettlePara");
        rv_goods.setLayoutManager(new LinearLayoutManager(mActivity));
        ensureOrderAdapter = new EnsureOrderAdapter();
        rv_goods.setAdapter(ensureOrderAdapter);


    }


    @Override
    public void loadData() {
        if (!isLoad){
            showProress();
        }
        if (requestSettlePara.type == RequestSettlePara.PRODUCT) {
            getDataForProduct();
        }else if (requestSettlePara.type == RequestSettlePara.CART) {
            getDataForCart();
        }else if (requestSettlePara.type == RequestSettlePara.GROUP) {
            getDataForGroup();
        }
    }

    private void getDataForProduct() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", requestSettlePara.product_id);
        hashMap.put("norm_id", requestSettlePara.norm_id);
        hashMap.put("number", requestSettlePara.number);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        new RxHttp<BaseResult<OrderSettleResult>>().send(ApiManager.getService().getSettlementProduct(hashMap),
                new Response<BaseResult<OrderSettleResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderSettleResult> result) {
                        showContent();
                        OrderSettleResult settleResult = result.data;
                        setData(settleResult);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    private void getDataForCart() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("cart_ids", requestSettlePara.cart_ids);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_ids", requestSettlePara.coupon_ids);
        new RxHttp<BaseResult<OrderSettleResult>>().send(ApiManager.getService().getSettlementCart(hashMap),
                new Response<BaseResult<OrderSettleResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderSettleResult> result) {
                        showContent();
                        OrderSettleResult settleResult = result.data;
                        setData(settleResult);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }

    private void getDataForGroup() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", requestSettlePara.product_id);
        hashMap.put("norm_id", requestSettlePara.norm_id);
        hashMap.put("number", requestSettlePara.number);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        new RxHttp<BaseResult<OrderSettleResult>>().send(ApiManager.getService().getSettlementGroup(hashMap),
                new Response<BaseResult<OrderSettleResult>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderSettleResult> result) {
                        showContent();
                        OrderSettleResult settleResult = result.data;
                        setData(settleResult);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });
    }



    private void setData(OrderSettleResult settleResult) {
        setAddress(settleResult.address);
        tvTaxTotal.setText("￥"+settleResult.total_ship);
        tvCouponTotal.setText("￥"+settleResult.total_dis);
        tvGoodsTotal.setText("￥"+settleResult.total_goods);
        tvOrderMoney.setText("￥"+settleResult.total_amount);
        tvPayMonet.setText("￥"+settleResult.total_amount);

        ensureOrderAdapter.setNewData(settleResult.list);


    }

    private void setAddress(NetCityBean address) {

        if (address == null || TextUtils.isEmpty(address.mobile)) {
            llNoAddress.setVisibility(View.VISIBLE);
            llHavaAddress.setVisibility(View.GONE);
        } else {
            llNoAddress.setVisibility(View.GONE);
            llHavaAddress.setVisibility(View.VISIBLE);
            requestSettlePara.address_id = address.id+"";
            tvName.setText(address.name + "   " + address.mobile);
            tvDetailAddress.setText(address.province + address.city + address.area + address.address);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MineAddressActivity.REQUESTCODE) {

            if (data == null){
                requestSettlePara.address_id = "0";
            }else{
                NetCityBean netCityBean = data.getParcelableExtra("netCityBean");
                requestSettlePara.address_id = netCityBean.id+"";
            }
            loadData();
        } else if (requestCode == 11 && resultCode == RESULT_OK) {
            SelectCouponsBean selectCouponsBean = data.getParcelableExtra("couponsBean");
                requestSettlePara.coupon_ids = CouponsStringUtil.getString(requestSettlePara.coupon_ids
                ,selectCouponsBean.id+"",!TextUtils.isEmpty(selectCouponsBean.title));
            loadData();
        }
    }


    @OnClick({R.id.tv_commit
            , R.id.ll_selete_address})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_commit:


                if (requestSettlePara.type == RequestSettlePara.PRODUCT){
                    createOrderForProduct();
                }else if (requestSettlePara.type == RequestSettlePara.CART){
                    createOrderForCart();
                }else if (requestSettlePara.type == RequestSettlePara.GROUP){
                    createOrderForGroup();
                }

                break;
            case R.id.ll_selete_address:
                MineAddressActivity.launch(mActivity, MineAddressActivity.SELETE, "");
                break;
//            case R.id.ll_coupon:
//                SelectCouponActivity.launch(mActivity);
//                break;

        }
    }

    private String getRemark(){
        List<OrderSettleShopBean> data = ensureOrderAdapter.getData();
        List<OrderRemark> orderRemarks = new ArrayList<>();
        for (OrderSettleShopBean orderSettleShopBean:data){
            orderRemarks.add(new OrderRemark(orderSettleShopBean.store_id,orderSettleShopBean.remark));
        }
        return new Gson().toJson(orderRemarks);
    }

    private void createOrderForProduct() {
        if ("0".equals(requestSettlePara.address_id)){
            tsg("请选择收货地址");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", requestSettlePara.product_id);
        hashMap.put("norm_id", requestSettlePara.norm_id);
        hashMap.put("number", requestSettlePara.number);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        hashMap.put("remarks", getRemark());
        new RxHttp<BaseResult<CreateOrderBean>>().send(ApiManager.getService().createOrderByProduct(hashMap),
                new Response<BaseResult<CreateOrderBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CreateOrderBean> result) {
                        CreateOrderBean settleResult = result.data;
                        SelectPayWayActivity.launch(mActivity, settleResult);
                         finish();
                    }
                });
    }

    private void createOrderForCart() {
        if ("0".equals(requestSettlePara.address_id)){
            tsg("请选择收货地址");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("cart_ids", requestSettlePara.cart_ids);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        hashMap.put("remarks", getRemark());
        new RxHttp<BaseResult<CreateOrderBean>>().send(ApiManager.getService().createOrderByCart(hashMap),
                new Response<BaseResult<CreateOrderBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CreateOrderBean> result) {
                        EventBus.getDefault().post(new UpdateCart());
                        CreateOrderBean settleResult = result.data;
                        settleResult.type = CreateOrderBean.CART;
                        SelectPayWayActivity.launch(mActivity, settleResult);
                        finish();
                    }
                });
    }


    private void createOrderForGroup() {
        if ("0".equals(requestSettlePara.address_id)){
            tsg("请选择收货地址");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", requestSettlePara.product_id);
        hashMap.put("norm_id", requestSettlePara.norm_id);
        hashMap.put("number", requestSettlePara.number);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        hashMap.put("remarks", getRemark());
        new RxHttp<BaseResult<CreateOrderBean>>().send(ApiManager.getService().createOrderByGroup(hashMap),
                new Response<BaseResult<CreateOrderBean>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<CreateOrderBean> result) {
                        CreateOrderBean settleResult = result.data;
                        settleResult.type = CreateOrderBean.GROUP;
                        SelectPayWayActivity.launch(mActivity, settleResult);
                        finish();
                    }
                });
    }

}