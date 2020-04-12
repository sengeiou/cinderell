package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.global.GlobalParam;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


public class WelcomeActivity extends BaseActivity {


    @BindView(R.id.tv_register_agreement)
    TextView tvRegisterAgreement;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
        context.finish();


    }


    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvRegisterAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvPrivacy.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void loadData() {



    }

    @Override
    public void initListener() {

    }


    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(WelcomeActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }


    @OnClick({R.id.tv_register_agreement, R.id.tv_privacy, R.id.tv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_agreement:
                break;
            case R.id.tv_privacy:
                break;
            case R.id.tv_open:
                GlobalParam.setFirstUse(true);
                MainActivity.launch(mActivity);
                break;
        }
    }
}
