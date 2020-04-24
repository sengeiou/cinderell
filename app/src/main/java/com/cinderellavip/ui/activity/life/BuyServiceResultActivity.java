package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BuyServiceResultActivity extends BaseActivity {


    @BindView(R.id.tv_content)
    TextView tvContent;

    public static void launch(Context from) {
        Intent intent = new Intent(from, BuyServiceResultActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("发送成功");
    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_service_result;
    }


    @OnClick(R.id.tv_sure)
    public void onClick() {
        finish();
    }
}
