package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectCityFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectCityActivity extends BaseActivity {



    public static void launch(Activity from) {
        Intent intent = new Intent(from, SelectCityActivity.class);
        from.startActivityForResult(intent, RequestCode.request_city);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择城市");

    }


    @Override
    public void loadData() {
        SelectCityFragment fragment = new SelectCityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_list, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_city;
    }


}
