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
import com.cinderellavip.bean.eventbus.UpdateMineScore;
import com.cinderellavip.bean.eventbus.UpdateMinebalance;
import com.cinderellavip.bean.net.mine.MineBalanceResult;
import com.cinderellavip.bean.score.WithDrawExplain;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.Utils;
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

    public static final int BALANCE = 0;
    public static final int SCORE = 1;
    private int type;

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_balance)
    TextView tv_balance;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_explain)
    TextView tv_explain;

    @BindView(R.id.tv_score_withdraw_explain)
    TextView tv_score_withdraw_explain;

    public static void launch(Context from) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, WithDrawActivity.class);
        from.startActivity(intent);
    }


    public static void launch(Context from,int type) {
        if (!Utils.isFastClick()){
            return;
        }
        Intent intent = new Intent(from, WithDrawActivity.class);
        intent.putExtra("type",type);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        type = getIntent().getIntExtra("type",BALANCE);
        if(type == BALANCE){
            setBackTitle("提现申请");
            setRightText("提现说明");
            tv_time.setText("到账时间：1~2个工作日");
        }if(type == SCORE){
            setBackTitle("积分提现");
            setRightText("提现记录");
            tv_explain.setVisibility(View.VISIBLE);
            tv_score_withdraw_explain.setVisibility(View.GONE);
        }
    }


    @Override
    public void loadData() {
        if(type == BALANCE){
            getBalance();
        }if(type == SCORE){
            withDrawExplain();
        }
    }
    private void getBalance(){
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
    private void withDrawExplain(){

        new RxHttp<BaseResult<WithDrawExplain>>().send(ApiManager.getService().withDrawExplain(),
                new Response<BaseResult<WithDrawExplain>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<WithDrawExplain> result) {
                        WithDrawExplain data = result.data;
                        tv_balance.setText(data.text_money);
                        tv_time.setText(data.text_fee);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_withdraw;
    }


    @OnClick({R.id.tv_login,R.id.tv_explain})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_explain:
                AgreementWebViewActivity.launch(mActivity,Constant.H17);
                break;
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

                if(type == BALANCE){
                    balanceWithDraw(phone,money);
                }if(type == SCORE){
                    scoreWithDraw(phone,money);
                 }
                break;
        }
    }
    //余额提现
    private void balanceWithDraw(String phone,String money){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("account", phone+"");
        hashMap.put("money", money+"");
        new RxHttp<BaseResult>().send(ApiManager.getService().applyWithDraw(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        EventBus.getDefault().post(new UpdateMinebalance());
                        CenterDialogUtil.showCommitSuccess(mContext,"您的提现申请已提交",()->{
                            finish();
                        });
                    }
                });

    }


    private void scoreWithDraw(String phone,String money){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("account", phone+"");
        hashMap.put("money", money+"");
        new RxHttp<BaseResult>().send(ApiManager.getService().withDrawApply(hashMap),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        EventBus.getDefault().post(new UpdateMineScore());
                        CenterDialogUtil.showCommitSuccess(mContext,"您的提现申请已提交",()->{
                            finish();
                        });
                    }
                });

    }
    @Override
    public void initListener() {
        super.initListener();
        tv_right.setOnClickListener(v -> {
            if(type == BALANCE){
                AgreementWebViewActivity.launch(mActivity, Constant.H3);
            }if(type == SCORE){
                WithDrawHistoryActivity.launch(mActivity,WithDrawHistoryActivity.SCORE);
            }

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
//                String trim = editable.toString().trim();
//                try {
//                    if (Integer.parseInt(trim)>99990){
//                        tsg("单次最多提现9999元");
//                    }
//                    if (trim.length()>4){
//                        etMoney.setText("9999");
//                        etMoney.setSelection(4);
//                    }
//                }catch (Exception e){
//
//                }


            }
        });
    }
}
