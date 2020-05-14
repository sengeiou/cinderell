package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.UploadImageResult;
import com.cinderellavip.bean.local.OperateProductBean;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.ListResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.OperatingProductsUtil3s;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.util.address.LocalCityUtil3s;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.progress.LoadingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


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


    private String first_category;
    private String second_category;
    private String seller_image;
    private String business_license;
    private String id_card_front;
    private String id_card_back;
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
                new RxHttp<BaseResult<ListResult<OperateProductBean>>>().send(ApiManager.getService().getOperateCate(),
                        new Response<BaseResult<ListResult<OperateProductBean>>>(isLoad, mActivity) {
                            @Override
                            public void onSuccess(BaseResult<ListResult<OperateProductBean>> result) {
                                OperatingProductsUtil3s.getInstance().showSelectDialog(mContext, result.data.list, (province, city) -> {
                                    first_category = province.name;
                                    second_category = city;
                                    tvBusinessCategory.setText(province.name + "-" + city);
                                });
                            }
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
                seller_image = "";
                ivStore.setImageResource(R.mipmap.add_upload_image);
                ivDeleteStore.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_business_license:
                business_license = "";
                ivBusinessLicense.setImageResource(R.mipmap.add_upload_image);
                ivDeleteBusinessLicense.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_id_card_front:
                id_card_front = "";
                ivIdCardFront.setImageResource(R.mipmap.add_upload_image);
                ivDeleteIdCardFront.setVisibility(View.GONE);
                break;
            case R.id.iv_delete_id_card_back:
                id_card_back = "";
                ivIdCardBack.setImageResource(R.mipmap.add_upload_image);
                ivDeleteIdCardBack.setVisibility(View.GONE);
                break;
        }
    }

    private void commit() {
       String name = etName.getText().toString().trim();
        String mobile = etPhone.getText().toString().trim();
        String seller_name = etShopName.getText().toString().trim();
        String area = etCityName.getText().toString().trim();
        String address = etContent.getText().toString().trim();
        String product = etMainProduct.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            tsg(etName.getHint().toString());
            return;
        }if (TextUtils.isEmpty(mobile)){
            tsg(etPhone.getHint().toString());
            return;
        }if (TextUtils.isEmpty(seller_name)){
            tsg(etShopName.getHint().toString());
            return;
        }if (TextUtils.isEmpty(area)){
            tsg(etCityName.getHint().toString());
            return;
        }if (TextUtils.isEmpty(address)){
            tsg(etContent.getHint().toString());
            return;
        }if (TextUtils.isEmpty(first_category)){
            tsg(tvBusinessCategory.getHint().toString());
            return;
        }if (TextUtils.isEmpty(product)){
            tsg(etMainProduct.getHint().toString());
            return;
        }if (TextUtils.isEmpty(seller_image)){
            tsg("请上传门店照片");
            return;
        }if (TextUtils.isEmpty(business_license)){
            tsg("请上传营业执照");
            return;
        }if (TextUtils.isEmpty(id_card_front)){
            tsg("请上传身份证正面");
            return;
        }if (TextUtils.isEmpty(id_card_back)){
            tsg("请上传身份证反面照片");
            return;
        }

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("name", name + "");
        hashMap.put("mobile", mobile + "");
        hashMap.put("seller_name", seller_name + "");
        hashMap.put("area", area + "");
        hashMap.put("address", address + "");
        hashMap.put("first_category", first_category + "");
        hashMap.put("second_category", second_category + "");
        hashMap.put("product", product + "");
        hashMap.put("seller_image", seller_image + "");
        hashMap.put("business_license", business_license + "");
        hashMap.put("id_card_front", id_card_front + "");
        hashMap.put("id_card_back", id_card_back + "");
        new RxHttp<BaseResult>().send(ApiManager.getService().applySupplier(hashMap),
                new Response<BaseResult>( mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        CenterDialogUtil.showApplySuccess(mContext,()->{
                            ApplyProductSupplierResultActivity.launch(mActivity);
                            mActivity.finish();
                        });
                    }
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
            String path = PhotoUtils.getInstance().getPath(mActivity, requestCode, data);
            compress(path);


        }

    }
    private void compress(String path){
        Luban.with(this)
                .load(path)
                .ignoreBy(100)
                .setTargetDir(Constant.ROOT_PATH)
                .filter(path1 -> !(TextUtils.isEmpty(path1) || path1.toLowerCase().endsWith(".gif")))
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LoadingUtils.show(mContext,"压缩图片中...");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LoadingUtils.dismiss();
                        upload(file.getAbsolutePath());
                    }
                    @Override
                    public void onError(Throwable e) {
                        tsg("图片压缩失败");
                        LoadingUtils.dismiss();
                    }
                }).launch();

    }

    private void upload(String path){
        List<MultipartBody.Part> parts = new ArrayList<>();
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        parts.add(part);
        new RxHttp<BaseResult<UploadImageResult>>().send(ApiManager.getService().getUploadImg(parts),
                new Response<BaseResult<UploadImageResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UploadImageResult> result) {

                        String url = result.data.url;
                        ImageUtil.loadNet(mContext, imageView, url);
                        if (imageView == ivStore){
                            seller_image = url;
                            ivDeleteStore.setVisibility(View.VISIBLE);
                        }else if (imageView == ivBusinessLicense){
                            business_license = url;
                            ivDeleteBusinessLicense.setVisibility(View.VISIBLE);
                        }else if (imageView == ivIdCardFront){
                            id_card_front = url;
                            ivDeleteIdCardFront.setVisibility(View.VISIBLE);
                        }else if (imageView == ivIdCardBack){
                            id_card_back = url;
                            ivDeleteIdCardBack.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

}
