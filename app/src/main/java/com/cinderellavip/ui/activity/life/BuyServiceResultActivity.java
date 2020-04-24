package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BuyServiceResultActivity extends BaseActivity {



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


}
