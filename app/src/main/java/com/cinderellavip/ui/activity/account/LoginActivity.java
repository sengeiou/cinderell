package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.LoginFinishSuccess;
import com.cinderellavip.bean.eventbus.LoginSuccess;
import com.cinderellavip.bean.eventbus.UpdateMineInfo;
import com.cinderellavip.bean.net.UserInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.KeyboardUtils;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_selete_phone)
    TextView tvSeletePhone;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity,boolean isFinishLogin) {
        GlobalParam.setLoginFinish(isFinishLogin);
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("登录");

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void login() {
        String phone = etPhone.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            tsg("请输入手机号");
            return;
        } else if (!CommonUtils.isMobileNO(phone)) {
            tsg("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            tsg("请输入登录密码");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("mobile", phone);
        hashMap.put("password", pass);
        new RxHttp<BaseResult<UserInfo>>().send(ApiManager.getService().getLogin(hashMap),
                new Response<BaseResult<UserInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UserInfo> result) {
                        UserInfo userInfo = result.data;
                        GlobalParam.setUserInfo(userInfo);
                        if (GlobalParam.getLoginFinish()){
                            EventBus.getDefault().post(new UpdateMineInfo());
                            EventBus.getDefault().post(new LoginSuccess());
                            GlobalParam.setLoginFinish(false);
                            KeyboardUtils.hideInput(mActivity);
                            finish();
                        }else {
                            MainActivity.launch(mActivity);
                        }


                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            etPhone.setText(data.getStringExtra("phone"));
            etPass.setText(data.getStringExtra("pass"));
            etPhone.setSelection(etPhone.getText().toString().trim().length());
            etPass.setSelection(etPass.getText().toString().trim().length());

        }
    }




    @OnClick({R.id.tv_forget, R.id.tv_register, R.id.tv_login, R.id.tv_switch, R.id.tv_weChat_login, R.id.tv_aLiPay_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                FastLoginActivity.launch(mActivity,FastLoginActivity.FORGET_PASS);
                break;
            case R.id.tv_register:
                RegisterActivity.launch(mActivity);
                break;
            case R.id.tv_login:
                login();
                break;
            case R.id.tv_switch:
                FastLoginActivity.launch(mActivity,FastLoginActivity.FAST_LOGIN);
                break;
            case R.id.tv_weChat_login:
                break;
            case R.id.tv_aLiPay_login:
                break;
        }
    }

    @Override
    public void onEvent(Object o) {
        super.onEvent(o);
        if (o instanceof LoginFinishSuccess){
            finish();
        }
    }
}
