package com.cinderellavip.ui.activity.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.ui.fragment.CartFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


public class CartActivity extends BaseActivity {


    public static void launch(Activity activity) {
//        if (!GlobalParam.getUserLogin()){
//            SelectLoginWayActivity.launch(activity,true);
//            return;
//        }
        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("购物车");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_container, CartFragment.newInstance(CartFragment.NOT_TITLE))
                .commit();
    }

    @Override
    public void loadData() {

    }
}
