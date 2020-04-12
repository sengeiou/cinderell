package com.cinderellavip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.ui.activity.account.LoginActivity;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    public static void launch(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        context.finish();


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }

    @OnClick(R.id.tv_login)
    public void onClick() {
        LoginActivity.launch(mActivity);
    }
}
