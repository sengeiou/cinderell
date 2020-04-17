package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;


import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateMineInfo;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.sign.SignUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

import butterknife.BindView;

public class ModifyNickNameActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;

    public static void launch(Activity activity, String name) {
        Intent intent = new Intent(activity, ModifyNickNameActivity.class);
        intent.putExtra("name",name);
        activity.startActivityForResult(intent,100);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setBackTitle("修改昵称");
        setRightText("保存");
        etName.setText(getIntent().getStringExtra("name"));
        etName.setSelection(etName.getText().toString().length());
//        userInfo = GlobalParam.getUser();
//        if (userInfo != null){
//            setData();
//        }
    }


    @Override
    public void loadData() {
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_nickname;
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                tsg("请输入昵称");
                return;
            }
            EventBus.getDefault().post(new UpdateMineInfo());
            Intent intent = new Intent();
            intent.putExtra("name",name);
            setResult(101,intent);
            finish();

        });

    }
}
