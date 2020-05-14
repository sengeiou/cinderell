package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.local.OperateProductBean;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.toast.ServicePeriodDialogUtil;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class BuyLongServiceActivity extends BaseActivity {


    @BindView(R.id.tv_address_tip)
    TextView tvAddressTip;
    @BindView(R.id.tv_name1)
    TextView tvName1;
    @BindView(R.id.tv_name2)
    TextView tvName2;
    @BindView(R.id.tv_name3)
    TextView tvName3;
    @BindView(R.id.ll_address_info)
    LinearLayout llAddressInfo;
    @BindView(R.id.tv_service_period)
    TextView tvServicePeriod;
    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_agreement)
    ImageView ivAgreement;
    @BindView(R.id.tv_register_agreement)
    TextView tvRegisterAgreement;

    public static void launch(Context from) {
        Intent intent = new Intent(from, BuyLongServiceActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {

        setBackTitle("购买长期服务");


    }


    @Override
    public void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_long_service;
    }


    @OnClick({R.id.ll_select_address, R.id.ll_service_period, R.id.ll_service_type, R.id.iv_agreement
            , R.id.tv_buy, R.id.tv_register_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_address:
                SelectServiceAddressActivity.launch(mActivity, SelectServiceAddressActivity.SELECT);
                break;
            case R.id.ll_service_period:
                selectServicePeriod();
                break;
            case R.id.ll_service_type:
                selectServiceType();
                break;
            case R.id.iv_agreement:
                isSelect = !isSelect;
                ivAgreement.setImageResource(isSelect ?R.mipmap.service_agreement_select:R.mipmap.service_agreement_default);
                break;
            case R.id.tv_register_agreement:
                WebViewActivity.launch(mActivity,"灰姑娘服务协议","https://www.baidu.com");
                break;
            case R.id.tv_buy:
                buy();
                break;
        }
    }

    private void buy(){
        if (netCityBean == null){
            tsg("请选择服务地址");
            return;
        }
        if (TextUtils.isEmpty(tvServicePeriod.getText().toString().trim())){
            tsg("请选择服务周期");
            return;
        }if (TextUtils.isEmpty(tvServiceType.getText().toString().trim())){
            tsg("请选择服务类型");
            return;
        }if (!isSelect){
            tsg("请阅读并勾选灰姑娘服务协议");
            return;
        }

        BuyServiceResultActivity.launch(mActivity);
        finish();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SelectServiceAddressActivity.REQUEST_CODE && resultCode == RESULT_OK) {
//            setAddress(new NetCityBean(true));
        }
    }

    NetCityBean netCityBean;
    private void setAddress(NetCityBean address) {
        netCityBean = address;
        if (address == null ) {
            tvAddressTip.setVisibility(View.VISIBLE);
            llAddressInfo.setVisibility(View.GONE);
        } else {
            tvAddressTip.setVisibility(View.GONE);
            llAddressInfo.setVisibility(View.VISIBLE);
        }
    }
    private boolean isSelect = false;

    private void selectServicePeriod(){
        List<String> list = new ArrayList<>();
        for (int i=1;i<=12;i++)
        list.add(i+"个月");
        ServicePeriodDialogUtil.showSelectDialog(mActivity,list,reason -> {
           tvServicePeriod.setText(reason);
        });
    }

    private void selectServiceType(){
        List<OperateProductBean> list = new ArrayList<>();




//        List<OperateProductSecondBean> list3 = new ArrayList<>();
//        list3.add(new OperateProductSecondBean("日常护理"));
//        list3.add(new OperateProductSecondBean("母婴护理"));
//        list3.add(new OperateProductSecondBean("母乳喂养"));
//        list3.add(new OperateProductSecondBean("日常看护"));
//        list3.add(new OperateProductSecondBean("生活起居"));
//
//        list.add(new OperateProductBean("日常清洁", list3));
//        list.add(new OperateProductBean("清理养护", list3));
//        list.add(new OperateProductBean("月嫂", list3));
//        list.add(new OperateProductBean("保姆", list3));
//        list.add(new OperateProductBean("育儿嫂", list3));
//
//        ServiceTypeDialogUtil.getInstance().showSelectDialog(mContext, list, (province, city) -> {
//            tvServiceType.setText(province.name + "-" + city.name);
//        });
    }
}
