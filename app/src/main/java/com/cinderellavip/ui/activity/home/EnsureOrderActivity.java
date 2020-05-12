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
import com.cinderellavip.bean.local.RequestSettlePara;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.order.OrderSettleResult;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.cinderellavip.ui.activity.order.SelectPayWayActivity;
import com.cinderellavip.util.DataUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

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
        if (requestSettlePara.type == RequestSettlePara.PRODUCT) {
            getDataForProduct();
        }
//
    }

    private void getDataForProduct() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("product_id", requestSettlePara.product_id);
        hashMap.put("norm_id", requestSettlePara.norm_id);
        hashMap.put("number", requestSettlePara.number);
        hashMap.put("address_id", requestSettlePara.address_id);
        hashMap.put("coupon_id", requestSettlePara.coupon_ids);
        new RxHttp<BaseResult<OrderSettleResult>>().send(ApiManager.getService().getSettlementProduct(hashMap),
                new Response<BaseResult<OrderSettleResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<OrderSettleResult> result) {
                        OrderSettleResult settleResult = result.data;
                        setData(settleResult);


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
//            tvName.setText(address.truename + "   " + address.phone);
//            tvDetailAddress.setText(address.prov + address.city + address.dist + address.detail);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MineAddressActivity.REQUESTCODE && resultCode == RESULT_OK) {
//            setAddress(new NetCityBean(true));
        } else if (requestCode == 11 && resultCode == RESULT_OK) {
//            tvCouponMoney.setText("-￥20");
        }
    }


    @OnClick({R.id.tv_commit
            , R.id.ll_selete_address})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_commit:
                SelectPayWayActivity.launch(mActivity, 0, "");
                finish();
//                DialogUtil.getInstance().showRealNameDialog(mContext,payString -> {

//                });
                break;
            case R.id.ll_selete_address:
                MineAddressActivity.launch(mActivity, MineAddressActivity.SELETE, "");
                break;
//            case R.id.ll_coupon:
//                SelectCouponActivity.launch(mActivity);
//                break;

        }
    }

}