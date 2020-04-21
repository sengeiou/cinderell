package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.mine.CouponCenterFragment;
import com.cinderellavip.ui.fragment.order.RefundFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * Created by Administrator on 2016/9/8.
 */
public class CouponCenterActivity extends BaseActivity {



    public static void launch(Context from) {
        Intent intent = new Intent(from, CouponCenterActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("领劵中心");
//        setRightIcon(R.mipmap.icon_search_black);



    }


    @Override
    public void loadData() {
        CouponCenterFragment fragment = CouponCenterFragment.newInstance(1);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }


}
