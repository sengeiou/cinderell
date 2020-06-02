package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.BrandDetailFragment;
import com.cinderellavip.util.Utils;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BrandDetailActivity extends BaseActivity{


    public static void launch(Context from,String id) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, BrandDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("id",id);
        from.startActivity(intent);
    }


    String id;
    @Override
    public void initView(Bundle savedInstanceState) {
       id = getIntent().getStringExtra("id");

    }

    @Override
    protected int getToolbarLayout() {
        return -1;
    }

    @Override
    public void loadData() {

        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
                BrandDetailFragment.newInstance(id)).commit();
//        getSupportFragmentManager().beginTransaction().add(R.id.content_container,
//                new ShopDetailFragment()).commit();


    }

    @Override
    public void initListener() {
    }

    @Override
    public int getLayoutId() {
        return -1;
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.base_content;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparentForImageViewInFragment(BrandDetailActivity.this, null);
        StatusBarUtil.setLightMode(this);
    }

}
