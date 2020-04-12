package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.R;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.TreeMap;

import butterknife.BindView;

/**
 *
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_selete_phone)
    TextView tv_selete_phone;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    public static final int REGISTER = 1, FORGET = 2,MODIFY = 3;
    private int type;

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", REGISTER);
        if (type == REGISTER) {
            setBackTitle("注册");
            tvLogin.setText("注册");
            etPass.setHint("密码");
        } else if (type == FORGET) {
            setBackTitle("忘记密码");
            tvLogin.setText("确定");
            etPass.setHint("新密码");
        }else if (type == MODIFY) {
            setBackTitle("修改密码");
            tvLogin.setText("确定");
            etPass.setHint("新密码");
        }

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }

    private boolean isSelete = false;


    String tag = "1";

    private void register() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        }
//        else if (!CommonUtils.isMobileNO(phone)) {
//            tsg("请输入正确的手机号");
//            return;
//        }
        if (TextUtils.isEmpty(code)) {
            tsg("请输入短信验证码");
            return;
        }if (TextUtils.isEmpty(pass)) {
            tsg("请输入密码");
            return;
        }

        if (type == REGISTER && !isSelete){
            tsg("请勾选用户协议");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();


        if (type == REGISTER) {
            hashMap.put("phone", phone);
            hashMap.put("authCode", code);
            hashMap.put("password", pass);
            hashMap.put("parent_id","0");
            hashMap.put("sign", SignUtil.getMd5(hashMap));
            register(hashMap);
        } else if (type == FORGET) {
            hashMap.put("phone", phone);
            hashMap.put("code", code);
            hashMap.put("password", pass);
            hashMap.put("sign", SignUtil.getMd5(hashMap));
            forgetPass(hashMap);

        }else if (type == MODIFY) {
            hashMap.put("phone", phone);
            hashMap.put("code", code);
            hashMap.put("new_password", pass);
            hashMap.put("sign", SignUtil.getMd5(hashMap));
            modifyPass(hashMap);

        }

    }

    private void register(TreeMap<String, String> hashMap) {

    }

    private void forgetPass(TreeMap<String, String> hashMap) {

    }

    private void modifyPass(TreeMap<String, String> hashMap) {

    }

    private void setResult(String phone, String pass) {
        Intent intent = new Intent();
        intent.putExtra("phone", phone);
        intent.putExtra("pass", pass);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getCode() {
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("phone", phone);
        String category = "1";
        if (type == REGISTER){
            category = "1";
        }else if (type == FORGET){
            category = "2";
        }else if (type == MODIFY){
            category = "5";
        }
        hashMap.put("category",  category);
        hashMap.put("tag",  tag);
        String area_code = "86";
        if (tag.equals("1")){
            area_code = "86";
        }else {
            area_code = "852";
        }
        hashMap.put("area_code",  area_code);
//        hashMap.put("sign", SignUtil.getMd5(area_code+category+phone+tag));
        hashMap.put("sign", SignUtil.getMd5(hashMap));

    }

    private int time = 60;
    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (time > 0) {
                time--;
                tvCode.setText(time + "s");
                tvCode.setTextColor(getResources().getColor(R.color.grayText));
                mHandler.sendEmptyMessageDelayed(1, 1000);
                tvCode.setEnabled(false);
            } else {
                time = 60;
                tvCode.setTextColor(getResources().getColor(R.color.red));
                tvCode.setText("获取");
                tvCode.setEnabled(true);
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null){
            mHandler.removeMessages(1);
        }
        mHandler = null;

    }


}
