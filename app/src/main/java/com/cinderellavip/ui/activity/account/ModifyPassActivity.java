package com.cinderellavip.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateMineInfo;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class ModifyPassActivity extends BaseActivity {


    @BindView(R.id.et_pass_advance)
    EditText etPassAdvance;
    @BindView(R.id.et_pass_new)
    EditText etPassNew;
    @BindView(R.id.et_pass_new_again)
    EditText etPassNewAgain;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, ModifyPassActivity.class);
        activity.startActivityForResult(intent, 100);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("修改登录密码");
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }


    private void register() {
        String pass = etPassAdvance.getText().toString().trim();
        String pass1 = etPassNew.getText().toString().trim();
        String pass2 = etPassNewAgain.getText().toString().trim();
        if (TextUtils.isEmpty(pass)) {
            tsg("请输入原密码");
            return;
        }
        if (TextUtils.isEmpty(pass1)) {
            tsg("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(pass2)) {
            tsg("请再次输入新登录密码");
            return;
        }if (!pass1.equals(pass2)) {
            tsg("两次密码不一致");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("old_password",pass);
        hashMap.put("new_password",pass2);
        new RxHttp<BaseResult>().send(ApiManager.getService().updatePass(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        tsg("修改成功");
                        finish();
                    }
                });




    }




    @OnClick(R.id.tv_login)
    public void onClick() {
        register();
    }
}
