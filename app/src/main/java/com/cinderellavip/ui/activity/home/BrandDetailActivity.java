package com.cinderellavip.ui.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.BrandDetailFragment;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.StatusBarUtil;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BrandDetailActivity extends BaseActivity{


    public static void launch(Context from,String id) {
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


//    @OnClick({R.id.iv_back, R.id.iv_search, R.id.iv_collect})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_back:
//                finish();
//                break;
//            case R.id.iv_search:
//                SearchActivity.launch(mActivity);
//                break;
//            case R.id.iv_collect:
//                break;
//        }
//    }
}
