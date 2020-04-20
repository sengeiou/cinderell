package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class SingleServiceOrderListActivity extends BaseActivity {


    public static final int ALL = 0;
    public static final int PAY = 1;
    public static final int SERVICE = 2;
    public static final int COMMENT = 3;

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, SingleServiceOrderListActivity.class);
        intent.putExtra("type",type);
        activity.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("全部订单");



    }


    @Override
    public void loadData() {
        RefundFragment fragment = new RefundFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
