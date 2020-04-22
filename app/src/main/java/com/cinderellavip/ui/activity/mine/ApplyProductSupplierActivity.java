package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.OperateProductBean;
import com.cinderellavip.bean.local.OperateProductSecondBean;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.OperatingProductsUtil3s;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.util.address.LocalCityUtil3s;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ApplyProductSupplierActivity extends CheckPermissionActivity {


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
    @BindView(R.id.iv_delete_store)
    ImageView ivDeleteStore;
    @BindView(R.id.iv_delete_business_license)
    ImageView ivDeleteBusinessLicense;
    @BindView(R.id.iv_delete_id_card_front)
    ImageView ivDeleteIdCardFront;
    @BindView(R.id.iv_delete_id_card_back)
    ImageView ivDeleteIdCardBack;

    public static void launch(Context from) {
        Intent intent = new Intent(from, ApplyProductSupplierActivity.class);
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


    @OnClick({R.id.et_city_name, R.id.tv_business_category, R.id.iv_store, R.id.iv_business_license
            , R.id.iv_id_card_front, R.id.iv_id_card_back, R.id.tv_login,
            R.id.iv_delete_store, R.id.iv_delete_business_license, R.id.iv_delete_id_card_front
            , R.id.iv_delete_id_card_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_city_name:
                LocalCityUtil3s.getInstance().showSelectDialog(mContext, ((province, city, county) -> {
                    etCityName.setText(province.name + "-" + city.name + "-" + county.name);
                }));
                break;
            case R.id.tv_business_category:
                List<OperateProductBean> list = new ArrayList<>();

                List<OperateProductSecondBean> list1 = new ArrayList<>();
                list1.add(new OperateProductSecondBean("全部"));
                list1.add(new OperateProductSecondBean("服务员"));
                list1.add(new OperateProductSecondBean("送餐员"));
                list1.add(new OperateProductSecondBean("厨师"));
                list.add(new OperateProductBean("生活|服务业", list1));

                List<OperateProductSecondBean> list2 = new ArrayList<>();
                list2.add(new OperateProductSecondBean("全部"));
                list2.add(new OperateProductSecondBean("前台"));
                list2.add(new OperateProductSecondBean("经理"));
                list2.add(new OperateProductSecondBean("人事"));
                list.add(new OperateProductBean("人力|行政|管理", list2));

                List<OperateProductSecondBean> list3 = new ArrayList<>();
                list3.add(new OperateProductSecondBean("全部"));
                list.add(new OperateProductBean("销售|客服|采购|淘宝", list3));
                list.add(new OperateProductBean("酒店", list3));


                OperatingProductsUtil3s.getInstance().showSelectDialog(mContext, list, (province, city) -> {
                    tvBusinessCategory.setText(province.name + "-" + city.name);
                });
                break;
            case R.id.iv_store:
                imageView = ivStore;
                checkPermissions(PhotoUtils.needPermissions);
                break;
            case R.id.iv_business_license:
                imageView = ivBusinessLicense;
                checkPermissions(PhotoUtils.needPermissions);
                break;
            case R.id.iv_id_card_front:
                imageView = ivIdCardFront;
                checkPermissions(PhotoUtils.needPermissions);
                break;
            case R.id.iv_id_card_back:
                imageView = ivIdCardBack;
                checkPermissions(PhotoUtils.needPermissions);
                break;
            case R.id.tv_login:
                commit();
                break;
            case R.id.iv_delete_store:
                ivStore.setImageResource(R.mipmap.add_upload_image);
                ivDeleteStore.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_business_license:
                ivBusinessLicense.setImageResource(R.mipmap.add_upload_image);
                ivDeleteBusinessLicense.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_id_card_front:
                ivIdCardFront.setImageResource(R.mipmap.add_upload_image);
                ivDeleteIdCardFront.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_id_card_back:
                ivIdCardBack.setImageResource(R.mipmap.add_upload_image);
                ivDeleteIdCardBack.setVisibility(View.GONE);
                break;
        }
    }

    private void commit() {
        CenterDialogUtil.showApplySuccess(mContext,()->{
            ApplyProductSupplierResultActivity.launch(mActivity);
            mActivity.finish();
        });
    }

    private ImageView imageView;
    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ImageUtil.loadLocal(mContext, imageView, (PhotoUtils.getInstance().getPath(mActivity, requestCode, data)));
            if (imageView == ivStore){
                ivDeleteStore.setVisibility(View.VISIBLE);
            }else if (imageView == ivBusinessLicense){
                ivDeleteBusinessLicense.setVisibility(View.VISIBLE);
            }else if (imageView == ivIdCardFront){
                ivDeleteIdCardFront.setVisibility(View.VISIBLE);
            }else if (imageView == ivIdCardBack){
                ivDeleteIdCardBack.setVisibility(View.VISIBLE);
            }
        }

    }

}
