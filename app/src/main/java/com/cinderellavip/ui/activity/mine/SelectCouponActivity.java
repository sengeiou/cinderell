package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.order.RequestSelectCoupons;
import com.cinderellavip.ui.fragment.mine.SelectCouponFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;


public class SelectCouponActivity extends BaseActivity {


    public static void launch(Activity activity, RequestSelectCoupons requestSelectCoupons) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, SelectCouponActivity.class);
        intent.putExtra("requestSelectCoupons",requestSelectCoupons);
        activity.startActivityForResult(intent,11);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("选择优惠券");
    }

    @Override
    public void loadData() {
        RequestSelectCoupons requestSelectCoupons = getIntent().getParcelableExtra("requestSelectCoupons");
        SelectCouponFragment fragment = SelectCouponFragment.newInstance(requestSelectCoupons);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();


    }


}
