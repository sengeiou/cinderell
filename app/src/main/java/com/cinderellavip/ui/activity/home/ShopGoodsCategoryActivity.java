package com.cinderellavip.ui.activity.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cinderellavip.R;
import com.cinderellavip.ui.fragment.home.MerchantGoodsCategoryFragment;
import com.tozzais.baselibrary.ui.BaseActivity;


/**
 * 商家 商家分类
 */
public class ShopGoodsCategoryActivity extends BaseActivity {


    int merchant_id;
    public static void launch(Activity activity, int merchant_id){
        Intent intent = new Intent(activity, ShopGoodsCategoryActivity.class);
        intent.putExtra("merchant_id",merchant_id);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_content;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        merchant_id = getIntent().getIntExtra("merchant_id",-1);
        setBackTitle("商品分类");
        MerchantGoodsCategoryFragment fragment = MerchantGoodsCategoryFragment.newInstance(merchant_id);
        getSupportFragmentManager().beginTransaction().add(R.id.content_container, fragment).commit();
    }

    @Override
    public void loadData() {

    }



}
