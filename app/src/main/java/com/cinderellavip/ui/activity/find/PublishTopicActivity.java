package com.cinderellavip.ui.activity.find;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.util.address.LocalCityUtil3s;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发布话题
 */
public class PublishTopicActivity extends CheckPermissionActivity {
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


    public static void launch(Context activity) {
        Intent intent = new Intent(activity, PublishTopicActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity, int type, NetCityBean item) {
        Intent intent = new Intent(activity, PublishTopicActivity.class);
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
                commit();
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
        LocalCityUtil3s.getInstance().showSelectDialog(mContext, ((province, city, county) -> {
            tvAddress.setText(province.name + "-" + city.name + "-" + county.name);
        }));
    }


    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }


    private void commit() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();
        String addressDetail = etAddress.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            tsg("请输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入联系号码");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("手机号码格式不正确");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            tsg("请选择所在城市");
            return;
        }
        if (TextUtils.isEmpty(addressDetail)) {
            tsg("请填写详细地址");
            return;
        }
        success();


    }

    private void success() {
        if (type == ADD) {
            tsg("新增成功");
        } else {
            tsg("修改成功");
        }
        finish();
    }




}
