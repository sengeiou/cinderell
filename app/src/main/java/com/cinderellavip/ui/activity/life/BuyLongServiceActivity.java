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
import com.cinderellavip.bean.ServiceType;
import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.bean.net.life.PreLongOrderResult;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.RequestCode;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.ServicePeriodDialogUtil;
import com.cinderellavip.toast.ServiceTypeDialogUtil;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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

    private String cycle;
    private String service;
    private String service_project;

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
                AgreementWebViewActivity.launch(mActivity, Constant.H5_SERVICE);
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

        preOrder();




    }
    private void preOrder(){
        String claim = etAddress.getText().toString().trim();
        TreeMap<String,String> map = new TreeMap<>();
        map.put("address",netCityBean.id+"");
        map.put("cycle",cycle+"");
        map.put("service",service+"");
        map.put("service_project",service_project+"");

        map.put("claim",claim+"");
        new RxHttp<BaseResult>().send(ApiManager.getService().preLongOrder(map),
                new Response<BaseResult>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        BuyServiceResultActivity.launch(mActivity,tvServiceType.getText().toString().trim());
                        finish();

                    }
                });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestCode.request_service_address && resultCode == RESULT_OK) {
            netCityBean  = data.getParcelableExtra("netCityBean");
            setAddress(netCityBean);
        }
    }

    LifeCityBean netCityBean;
    private void setAddress(LifeCityBean address) {
        netCityBean = address;
        if (address == null ) {
            tvAddressTip.setVisibility(View.VISIBLE);
            llAddressInfo.setVisibility(View.GONE);
        } else {
            tvAddressTip.setVisibility(View.GONE);
            llAddressInfo.setVisibility(View.VISIBLE);
            tvName1.setText(netCityBean.title);
            tvName2.setText(netCityBean.address+netCityBean.doorplate);
            tvName3.setText(netCityBean.name+netCityBean.phone);
        }

    }
    private boolean isSelect = false;

    private void selectServicePeriod(){
        List<String> list = new ArrayList<>();
        for (int i=1;i<=12;i++)
        list.add(i+"个月");
        ServicePeriodDialogUtil.showSelectDialog(mActivity,list,
                (reason, position) -> {
                    tvServicePeriod.setText(reason);
                    cycle = position+"";
                });
    }

    private void selectServiceType(){

        new RxHttp<BaseResult<List<ServiceType>>>().send(ApiManager.getService().lifeCategory(),
                new Response<BaseResult<List<ServiceType>>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<List<ServiceType>> result) {
                        ServiceTypeDialogUtil.getInstance().showSelectDialog(mContext, result.data, (province, city) -> {
                            tvServiceType.setText(province.title + "-" + city.title);
                            service = province.id+"";
                            service_project = city.id+"";

                        });
                    }
                });


    }
}
