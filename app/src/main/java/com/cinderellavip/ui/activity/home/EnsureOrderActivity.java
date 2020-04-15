package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.adapter.listview.EnsureOrderAdapter;
import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.Constant;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.activity.mine.MineAddressActivity;
import com.cinderellavip.ui.activity.mine.SeleteCouponActivity;
import com.cinderellavip.util.DataUtil;
import com.cinderellavip.weight.MyListView;
import com.tozzais.baselibrary.ui.BaseActivity;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class EnsureOrderActivity extends BaseActivity {


    @BindView(R.id.lv_goods)
    MyListView lvGoods;


    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.tv_pay_monet)
    TextView tvPayMonet;

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

    @BindView(R.id.tv_goods_money)
    TextView tvGoodsMoney;



    public static void launch(Context activity) {
        Intent intent = new Intent(activity, EnsureOrderActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_ensure_order;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("确认订单");

    }

    EnsureOrderAdapter ensureOrderAdapter;

    @Override
    public void loadData() {
       setData();
    }






    private void setData() {

        setAddress(null);

        ensureOrderAdapter = new EnsureOrderAdapter(DataUtil.getData(2), mContext);
        lvGoods.setAdapter(ensureOrderAdapter);



    }

    private void setAddress(NetCityBean address) {

        if (address == null ) {
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
           setAddress(new NetCityBean(true));
        } else if (requestCode == 11 && resultCode == RESULT_OK) {
            tvCouponMoney.setText("-￥20");
        }
    }



    @OnClick({R.id.tv_commit
            , R.id.ll_selete_address, R.id.ll_coupon})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_commit:
//                DialogUtil.getInstance().showRealNameDialog(mContext,payString -> {

//                });
                break;
            case R.id.ll_selete_address:
                MineAddressActivity.launch(mActivity, MineAddressActivity.SELETE, "");
                break;
            case R.id.ll_coupon:
                SeleteCouponActivity.launch(mActivity);
                break;

        }
    }

}