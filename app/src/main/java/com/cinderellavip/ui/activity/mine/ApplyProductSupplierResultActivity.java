package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.util.address.LocalCityUtil3s;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ApplyProductSupplierResultActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_shop_name)
    EditText etShopName;
    @BindView(R.id.et_city_name)
    TextView etCityName;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_business_category)
    TextView tvBusinessCategory;
    @BindView(R.id.et_main_product)
    EditText etMainProduct;
    @BindView(R.id.iv_store)
    ImageView ivStore;
    @BindView(R.id.iv_business_license)
    ImageView ivBusinessLicense;
    @BindView(R.id.iv_id_card_front)
    ImageView ivIdCardFront;
    @BindView(R.id.iv_id_card_back)
    ImageView ivIdCardBack;

    public static void launch(Context from) {
        Intent intent = new Intent(from, ApplyProductSupplierResultActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();

        setBackTitle("申请产品供应商");


    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_product_support;
    }


    @OnClick({R.id.et_city_name, R.id.tv_business_category, R.id.iv_store, R.id.iv_business_license, R.id.iv_id_card_front, R.id.iv_id_card_back, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_city_name:
                LocalCityUtil3s.getInstance().showSelectDialog(mContext, ((province, city, county) -> {
                    etCityName.setText(province.name + "-" + city.name + "-" + county.name);
                }));
                break;
            case R.id.tv_business_category:
                break;
            case R.id.iv_store:
                break;
            case R.id.iv_business_license:
                break;
            case R.id.iv_id_card_front:
                break;
            case R.id.iv_id_card_back:
                break;
            case R.id.tv_login:
                break;
        }
    }
}
