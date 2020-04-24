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

import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;

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


    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", ADD);
        if (type == ADD) {
            setBackTitle("新增服务地址");
        } else {
            setBackTitle("编辑服务地址");
        }

    }


    @Override
    public void loadData() {

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
                break;
            case R.id.ll_address:
                SelectLocationActivity.launch(mActivity);
                break;
            case R.id.tv_save:
                commit();
                break;
        }
    }

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
        tsg("添加成功");
        finish();


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
        }
    }
}
