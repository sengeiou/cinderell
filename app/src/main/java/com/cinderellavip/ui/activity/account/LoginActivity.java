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
import com.cinderellavip.bean.BindLogin;
import com.cinderellavip.bean.eventbus.LoginFinishSuccess;
import com.cinderellavip.bean.eventbus.LoginSuccess;
import com.cinderellavip.bean.eventbus.UpdateMineInfo;
import com.cinderellavip.bean.net.UserInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.util.KeyboardUtils;
import com.cinderellavip.util.Utils;
import com.cinderellavip.util.alilogin.AliUtil;
import com.cinderellavip.util.alilogin.OrderInfoUtil2_0;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements UMAuthListener {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_selete_phone)
    TextView tvSeletePhone;

    public static void launch(Activity activity) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void launch(Activity activity,boolean isFinishLogin) {
        if (!Utils.isFastClick()){
            return;
        }
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



    public static final String APPID = "2021001152637888";
    public static final String PID = "2088731816506920";
    public static final String RSA2PrivateKey = "MIIEowIBAAKCAQEApnlY49pXbCxQz1IbIt4i8g0ey+0myT1OAfuVjCGsA+4a4IXUyDnhyE3LPXamp9eyyPZo7H++p9irPU5UCELb7BLtYJX0dGcU7pgbMrD2WjIab1eth2RjniyqcDBW9ehCcjVEll6vF9q0oIVeLtaQFxToEHxuBiwNfse89ZiZ/z/gq+gIokQx4EbVx5Me7qdkfM/s0bIjeYiewrYLHS1CUfCWT5ZRBaXSb7A3ac9EU5vy5UWSuSQoTqYmKYHny7JYmjWksNvC4HRLUorzdkl9HOb+3nDcq98ZZpkjPEDkbLjY4kDZ3KBjLqQDLc1TgjxcFkQKBL8HrW3Y9Q5bRFyFSQIDAQABAoIBAQClntp5rbMGEry+1g/bS6b2q2j1N1Y8JbqjLFonFSTesBebQmpkXXmsXwERgCbjmyvq1YOjrp/Ouzf6ASCHK4VCw73YNrgCyr8/dKKydu5Q48H6/f7NUWnuZr+WZ/FMKytYFnjLJR4LnHHRtX3zGVI3NHyAUlrf1xoCBNOQf6bOEmqW3H+5I188wkW9oKy71qDM79061f+PyydED4Bf5kXz8B9Ch5vwQILMfUqFd3SOa6k1FoJ4Orr0iJ/Qe/Y1rXmGQBJZMKytVwsjZpDkkpamzT1txgKecNpge1l5HgbUD5bis684FxTsbaX26PVeNhFEFOm0a+V7M2dDMlnGGA4BAoGBAP1hRS6L8Rcb5107zc0PkKnMdO3NyAph9iD6qYP2WtND8LpcjjzkQjhwDabg8LfNOIoss81DJ5bhYNFF+E+/X9JjfYDoSrMt4q9267UCXkP84uKvOAjU3oyjBjCMQfMHVooijS6gcDAf3B0dN3ZPRitko/K6yUdhgoDmbtDFADnBAoGBAKgyBo76Zne65I4zNQKq7hzmTu8QVyFfng3aXbr6PXI89DqIJlhEsVmweuScIcd6qNf0YVKA0Imfy96YocwGs3vkfO+2KfTof3WhJwbYArimgTWoiyYsSq/2TJmtDJZOGGobNdip3Gpnsq7o2fIDBwdAPorZ2XFZgz9fuQ3p4t2JAoGAIcltszY/8Mfswxbta3Gu5aV41hkBmrjTXDRwlEMue0ts9zzOTrpVRjdS6rTGBIxeBLbPRwi0Z78n7N/6q4+mVGSl4KGZDGmK98DQz3NAZKp9CqM4wpy4DGLUuXaKeMC6vnK7BsOoEhK9hDhYI/OQNMsXvY9Atb7/6vZPQpFyZUECgYAfwcJHQLQUKy94QebiaA+pWTrSHI22+WzIRNCpuxHa/fNHEenbhKzPuGibvXcLSPeUPsoZX8UAFzHRB5SVBzbgT5XfVM+7NCLMvO63dhWdc6K11Org/D/l1WVFJs1Xm8s0TYrvK7M4S/gK2mA8N6IeFl04rkVGB5amYpZ4MA7rsQKBgGV+9ahk2bT2X01kTPK2WKO1jsX1RJWMXeEXKaNC5PpE1smxGar0L1FBjPkqWrFhWmkjdJ4/3jumtWutwckJgyyFLrqwMludNinwg8okTcNXXpeyjAk39npEUfziFHn7Cfyia5hQswAZg/PpU6MmJfOYVM4dLBepn989hrAsePHE";
    public static final String TARGET_ID = "1587824101";

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
                UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, this);
                break;
            case R.id.tv_aLiPay_login:
                try {
                    aliLogin();
                }catch (Exception e){
                    tsg(e.getMessage());
                }
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

    private void aliLogin(){
        boolean rsa2 = (RSA2PrivateKey.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String privateKey = rsa2 ? RSA2PrivateKey : RSA2PrivateKey;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        AliUtil.login(mActivity, authInfo, new AliUtil.Back() {
            @Override
            public void success(String result) {
                String[] split = result.split("&");
                String id = "";
                for (String s:split){
                    if (s.contains("alipay_open_id=")){
                        id = s.replace("alipay_open_id=","");
                        break;
                    }
                }
                BindLogin bindLogin = new BindLogin();
                bindLogin.unionid = id;
                bindLogin.nickname = "";
                bindLogin.sex = "";
                bindLogin.avatar = "";
                runOnUiThread(() -> {
                    isBind(bindLogin,"3");
                    LogUtil.e(bindLogin.toString());
                });
            }
            @Override
            public void failed() {
                LogUtil.e("授权失败");
            }
        });
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        LogUtil.e("onStart");

    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        String uid = map.get("uid");
        BindLogin bindLogin = new BindLogin();
        String name = map.get("name");
        String gender = "1";
        if ("男".equals(map.get("gender"))){
            gender = "1";
        }else if ("女".equals(map.get("gender"))){
            gender = "2";
        }
        String iconurl = map.get("iconurl");

        bindLogin.unionid = uid;
        bindLogin.nickname = name;
        bindLogin.sex = gender;
        bindLogin.avatar = iconurl;
        isBind(bindLogin,"2");
    }

    /**
     *
     * @param type 2微信，3支付宝
     */
    private void isBind( BindLogin bindLogin ,String type){

        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("unionid", bindLogin.unionid);
        hashMap.put("type", type);
        new RxHttp<BaseResult<UserInfo>>().send(ApiManager.getService().isBind(hashMap),
                new Response<BaseResult<UserInfo>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<UserInfo> result) {
                        UserInfo userInfo = result.data;
                        if (userInfo.is_bind){
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
                        }else {
                            FastLoginActivity.launch(mActivity,bindLogin);
                        }
                    }
                });
    }

   @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        LogUtil.e("onError");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        LogUtil.e("onCancel");
    }
}
