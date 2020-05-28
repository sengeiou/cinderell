package com.cinderellavip.ui.activity.life;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateLifeAddress;
import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.map.LocationUtil;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class AddServiceAddressActivity extends CheckPermissionActivity {
    public static final int ADD = 1, EDIT = 2;
    public static final int REQUESTCODE = 101;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_house_number)
    EditText etHouseNumber;
    @BindView(R.id.cb_default_address)
    CheckBox cbDefaultAddress;
    private int type;


    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, AddServiceAddressActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, REQUESTCODE);
    }

    private LifeCityBean item;
    public static void launch(Activity activity, int type, LifeCityBean item) {
        Intent intent = new Intent(activity, AddServiceAddressActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("item", item);
        activity.startActivityForResult(intent, REQUESTCODE);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", ADD);
        if (type == ADD) {
            setBackTitle("新增服务地址");
        } else {
            setBackTitle("编辑服务地址");
            item = getIntent().getParcelableExtra("item");
            etName.setText(item.name);
            etPhone.setText(item.phone);
            tvAddress.setText(item.title);
            tvAddressDetail.setText(item.address);
            etHouseNumber.setText(item.doorplate);
            isDefault = item.isDefault();
            cbDefaultAddress.setChecked(item.isDefault());

            if (item.isGender())
                rbWoman.setChecked(true);
            else
                rbMan.setChecked(true);


        }

    }


    @Override
    public void loadData() {
            location();
    }

    private void location(){
        if (type == ADD)
        LocationUtil.getInstance().start(mActivity,(aMapLocation, lat, lnt) -> {
            if (aMapLocation.getErrorCode() == 0){
                tvAddress.setText(aMapLocation.getPoiName());
                tvAddressDetail.setText(aMapLocation.getProvince()+
                        aMapLocation.getCity()+ aMapLocation.getDistrict()+
                        aMapLocation.getStreet()+aMapLocation.getStreetNum());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_address_edit_service;
    }


    @OnClick({R.id.tv_select_address_book, R.id.cb_default_address,R.id.ll_address, R.id.tv_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_address_book:
                checkPermissions(needPermissions);
                break;
            case R.id.cb_default_address:
                isDefault = !isDefault;
                cbDefaultAddress.setChecked(isDefault);
                break;
            case R.id.ll_address:
                SelectLocationActivity.launch(mActivity);
                break;
            case R.id.tv_save:
                commit();
                break;
        }
    }

    public boolean isDefault;

    private void commit(){
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String house_number = etHouseNumber.getText().toString().trim();
        if (TextUtils.isEmpty(name)){
            tsg("请输入姓名");
            return;
        }if (TextUtils.isEmpty(phone)){
            tsg("请输入手机号码");
            return;
        }if (TextUtils.isEmpty(house_number)){
            tsg("请输入门牌号");
            return;
        }


        TreeMap<String, String> hashMap = new TreeMap<>();
        if (type == EDIT){
            hashMap.put("addressannal", ""+item.id);
        }
        hashMap.put("name", ""+name);
        hashMap.put("phone", ""+phone);
        hashMap.put("title", ""+tvAddress.getText().toString().trim());
        hashMap.put("address", ""+tvAddressDetail.getText().toString().trim());
        hashMap.put("doorplate", house_number);
        hashMap.put("gender", rbMan.isChecked()?"0":"1");
        hashMap.put("default", isDefault?"1":"0");
        new RxHttp<BaseResult>().send(ApiManager.getService().editLifeAddress(hashMap),
                new Response<BaseResult>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {

                        if (type == ADD) {
                            tsg("添加成功");
                        } else {
                            tsg("修改成功");
                        }

                        EventBus.getDefault().post(new UpdateLifeAddress());
                        finish();

                    }

                });


    }

    @Override
    public void initListener() {
        super.initListener();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
    }

    public static String[] needPermissions = {
            Manifest.permission.READ_CONTACTS
    };
    @Override
    public void permissionGranted() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, 1002);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1002) {
            if (data != null) {
                Uri uri = data.getData();
                String phoneNum = null;
                String contactName = null;
                // 创建内容解析者
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = null;
                if (uri != null) {
                    cursor = contentResolver.query(uri,
                            new String[]{"display_name","data1"}, null, null, null);
                }
                while (cursor.moveToNext()) {
                    contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                cursor.close();
                //  把电话号码中的  -  符号 替换成空格
                if (phoneNum != null) {
                    phoneNum = phoneNum.replaceAll("-", " ");
                    // 空格去掉  为什么不直接-替换成"" 因为测试的时候发现还是会有空格 只能这么处理
                    phoneNum= phoneNum.replaceAll(" ", "");
                }
                etPhone.setText(phoneNum);
            }
        }else if (requestCode == RequestCode.request_nearby && resultCode == RESULT_OK){
            PoiItem netCityBean = data.getParcelableExtra("netCityBean");
            tvAddress.setText(netCityBean.getTitle());
            tvAddressDetail.setText(netCityBean.getProvinceName()+netCityBean.getCityName()+netCityBean.getAdName()+netCityBean.getSnippet());
        }
    }
}
