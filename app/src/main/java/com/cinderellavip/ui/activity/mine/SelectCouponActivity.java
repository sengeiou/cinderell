package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.SeleteCouponFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


public class SelectCouponActivity extends BaseActivity {


    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SelectCouponActivity.class);
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

        getSupportFragmentManager().beginTransaction().add(R.id.content_container, SeleteCouponFragment.newInstance()).commit();


    }


}
