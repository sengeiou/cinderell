package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.util.PhotoUtils;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditAddressActivity extends CheckPermissionActivity {
    public static final int ADD = 1, EDIT = 2;
    public static final int REQUESTCODE = 101;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.cb_default_address)
    CheckBox cbDefaultAddress;
    @BindView(R.id.tv_sava)
    TextView tvSava;

    private int type;

    //只有地址编辑的时候 才会有这个数据
    private NetCityBean item;


    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, EditAddressActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, REQUESTCODE);
    }

    public static void launch(Activity activity, int type, NetCityBean item) {
        Intent intent = new Intent(activity, EditAddressActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("item", item);
        activity.startActivityForResult(intent, REQUESTCODE);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", ADD);
        if (type == ADD) {
            tvSava.setText("添加收货地址");
            setBackTitle("新增地址");
        } else {
            tvSava.setText("修改收货地址");
            item = getIntent().getParcelableExtra("item");
            setBackTitle("编辑地址");
            setRightText("删除");
            setData();
        }
    }

    @Override
    public void loadData() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit;
    }


    private void setData() {
//        etName.setText(item.truename);
//        etPhone.setText(item.phone);
//        tvAddress.setText(String.format("%s %s %s", item.prov, item.city, item.dist));
//        etAddress.setText(item.detail);
//        cbDefaultAddress.setChecked(item.isDefault());


    }








    @OnClick({R.id.ll_address, R.id.tv_sava, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                getCity();
                break;
            case R.id.tv_sava:
                break;
            case R.id.tv_right:
//                CenterDialogUtil.showTwo(mContext, "确定要删除收货地址吗？",
//                        "删除后不可恢复，请谨慎操作。", "暂不删除", "确认删除", type1 -> {
//                            if (type1 == 1) {
//                                TreeMap<String, String> hashMap = new TreeMap<>();
//                                hashMap.put("user_id", GlobalParam.getUserId());
//                                hashMap.put("address_id", item.address_id + "");
//                                hashMap.put("sign", SignUtil.getMd5(hashMap));
//
//                                new RxHttp<BaseResult>().send(ApiManager.getService().removeAddress(hashMap),
//                                        new Response<BaseResult>(mContext) {
//                                            @Override
//                                            public void onSuccess(BaseResult result) {
//                                                tsg("删除成功");
//                                                EventBus.getDefault().post(new UpdateAddress());
//                                                finish();
//                                            }
//                                        });
//                            }
//                        });

                break;


        }
    }

    private void getCity() {


//
    }


    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }




}
