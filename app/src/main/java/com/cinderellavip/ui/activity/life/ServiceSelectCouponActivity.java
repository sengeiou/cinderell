package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectServiceCouponFragment;
import com.cinderellavip.ui.fragment.mine.SelectCouponFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceSelectCouponActivity extends BaseActivity {



    public static void launch(Activity from) {
        Intent intent = new Intent(from, ServiceSelectCouponActivity.class);
        from.startActivityForResult(intent, RequestCode.request_service_coupon);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择优惠券");



    }


    @Override
    public void loadData() {
        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                SelectServiceCouponFragment.newInstance()).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
