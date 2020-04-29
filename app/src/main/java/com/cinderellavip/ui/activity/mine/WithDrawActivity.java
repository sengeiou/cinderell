package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cinderellavip.R;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.tozzais.baselibrary.ui.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class WithDrawActivity extends BaseActivity {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_money)
    EditText etMoney;

    public static void launch(Context from) {
        Intent intent = new Intent(from, WithDrawActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("提现申请");
        setRightText("提现说明");
//        setRightIcon(R.mipmap.icon_search_black);


    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }


    @OnClick({R.id.tv_login})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_login:
                String phone = etPhone.getText().toString().trim();
                String money = etMoney.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    tsg("请输入支付宝账号");
                    return;
                }if (TextUtils.isEmpty(money)){
                    tsg("请输入提现金额");
                    return;
                }
                CenterDialogUtil.showCommitSuccess(mContext,()->{
                    finish();
                });
                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            WebViewActivity.launch(mActivity,"提现规则","https://www.baidu.com");
        });
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                if (Integer.parseInt(trim)>99990){
                    tsg("单次最多提现9999元");
                }
                if (trim.length()>4){
                    etMoney.setText("9999");
                    etMoney.setSelection(4);
                }

            }
        });
    }
}
