package com.cinderellavip.ui.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.UpdateMinebalance;
import com.cinderellavip.bean.net.mine.MineBalanceResult;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.TreeMap;

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
    @BindView(R.id.tv_balance)
    TextView tv_balance;

    public static void launch(Context from) {
        Intent intent = new Intent(from, WithDrawActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("提现申请");
        setRightText("提现说明");

    }


    @Override
    public void loadData() {
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("page", 1+"");
        hashMap.put("limit", 1+"");
        new RxHttp<BaseResult<MineBalanceResult>>().send(ApiManager.getService().mineBalance(hashMap),
                new Response<BaseResult<MineBalanceResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<MineBalanceResult> result) {
                        MineBalanceResult data = result.data;
                        tv_balance.setText("账户余额：￥"+data.balance);
                    }
                });

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
                }else if (Double.parseDouble(money)<1){
                    tsg("提现金额不得小于1元");
                    return;
                }
                TreeMap<String, String> hashMap = new TreeMap<>();
                hashMap.put("account", phone+"");
                hashMap.put("money", money+"");

                new RxHttp<BaseResult>().send(ApiManager.getService().applyWithDraw(hashMap),
                        new Response<BaseResult>(mActivity) {
                            @Override
                            public void onSuccess(BaseResult result) {
                                EventBus.getDefault().post(new UpdateMinebalance());
                                CenterDialogUtil.showCommitSuccess(mContext,()->{
                                    finish();
                                });
                            }
                        });

                break;
        }
    }

    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            AgreementWebViewActivity.launch(mActivity, Constant.H3);
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
                try {
                    if (Integer.parseInt(trim)>99990){
                        tsg("单次最多提现9999元");
                    }
                    if (trim.length()>4){
                        etMoney.setText("9999");
                        etMoney.setSelection(4);
                    }
                }catch (Exception e){

                }


            }
        });
    }
}
