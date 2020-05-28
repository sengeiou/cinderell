package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.ui.fragment.life.SelectLocationFragment;
import com.tozzais.baselibrary.ui.BaseActivity;

import androidx.annotation.Nullable;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SelectLocationActivity extends BaseActivity {



    public static void launch(Activity from) {
        Intent intent = new Intent(from, SelectLocationActivity.class);
        from.startActivityForResult(intent,RequestCode.request_nearby);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("选择地点");

    }


    @Override
    public void loadData() {
        fragment = new SelectLocationFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


    SelectLocationFragment fragment;
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_city && resultCode == Activity.RESULT_OK){
            fragment.setAddress(data.getStringExtra("name"));
        }
    }
}
