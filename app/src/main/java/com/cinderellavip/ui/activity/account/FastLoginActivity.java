package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.MainActivity;
import com.cinderellavip.R;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.sign.SignUtil;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class FastLoginActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_switch)
    TextView tvSwitch;
    @BindView(R.id.tv_selete_phone)
    TextView tv_selete_phone;

    public static final int FAST_LOGIN = 1, BIND_PHONE = 2;

    private int type;

    public static void launch(Activity activity, int type) {
        Intent intent = new Intent(activity, FastLoginActivity.class);
        intent.putExtra("type", type);
        activity.startActivityForResult(intent, 100);
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_login_fast;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getIntent().getIntExtra("type", FAST_LOGIN);
        if (type == FAST_LOGIN) {
            setBackTitle("快捷登录");
            tvLogin.setText("登录");
            tvSwitch.setVisibility(View.VISIBLE);
        } else if (type == BIND_PHONE) {
            setBackTitle("绑定手机号");
            tvLogin.setText("确定");
            tvSwitch.setVisibility(View.GONE);
        }

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
            tsg("请输入短信验证码");
            return;
        }
        if (type == FAST_LOGIN) {
        } else if (type == BIND_PHONE) {

        }


//
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
