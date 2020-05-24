package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectServiceCouponFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceSelectCouponActivity extends BaseActivity {



    //后期删除
    public static void launch(Activity from) {
        Intent intent = new Intent(from, ServiceSelectCouponActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_coupon);
    }


    public static void launch(Activity from,String contracts_id) {
        Intent intent = new Intent(from, ServiceSelectCouponActivity.class);
        intent.putExtra("contracts_id",contracts_id);
        from.startActivityForResult(intent, RequestCode.request_service_coupon);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择优惠券");



    }


    @Override
    public void loadData() {
        String contracts_id = getIntent().getStringExtra("contracts_id");
        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                SelectServiceCouponFragment.newInstance(contracts_id)).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
