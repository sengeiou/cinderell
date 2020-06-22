package com.cinderellavip.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinderellavip.R;
import com.cinderellavip.bean.eventbus.AddAddress;
import com.cinderellavip.bean.eventbus.OrderRefund;
import com.cinderellavip.bean.eventbus.UpdateLongServiceOrder;
import com.cinderellavip.bean.eventbus.UpdateShortServiceOrder;
import com.cinderellavip.bean.net.order.InvoiceBean;
import com.cinderellavip.global.Constant;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.toast.CenterDialogUtil;
import com.cinderellavip.toast.InvoiceDialogUtil;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.cinderellavip.util.PhotoUtils;
import com.cinderellavip.util.Utils;
import com.cinderellavip.util.address.LocalCityUtil3s;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.CheckPermissionActivity;
import com.tozzais.baselibrary.util.CommonUtils;
import com.tozzais.baselibrary.util.progress.LoadingUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;

public class EditInvoiceActivity extends CheckPermissionActivity {

    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ll_type)
    LinearLayout llType;
    @BindView(R.id.tv_type_person)
    TextView tvTypePerson;
    @BindView(R.id.tv_type_company)
    TextView tvTypeCompany;
    @BindView(R.id.et_company_name)
    EditText etCompanyName;
    @BindView(R.id.et_company_code)
    EditText etCompanyCode;
    @BindView(R.id.ll_company_invoice)
    LinearLayout llCompanyInvoice;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.tv_commit)
    TextView tvCommit;


    private int invoice_status;
    private int invoice_state;
    private String order_no;



    public static void launch(Activity activity, int invoice_status, int invoice_state, String order_no) {
        if (!Utils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(activity, EditInvoiceActivity.class);
        intent.putExtra("invoice_status", invoice_status);
        intent.putExtra("invoice_state", invoice_state);
        intent.putExtra("order_no", order_no);
        activity.startActivity(intent);
    }

    List<String> list = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        setLineVisibility();
        invoice_status = getIntent().getIntExtra("invoice_status", 0);
        invoice_state = getIntent().getIntExtra("invoice_state", 0);
        order_no = getIntent().getStringExtra("order_no");

        if (invoice_state == 1){
            list.add("电子普通发票");
        }else if (invoice_state == 2){
            list.add("纸质普通发票");
        }else if (invoice_state == 3){
            list.add("电子普通发票");
            list.add("纸质普通发票");
        }
        setBackTitle("填写发票信息");
        tvType.setText(list.get(0));
        setEmailVisible(invoice_state != 2);

        if (invoice_status == 0){
            //未申请
            tvCommit.setText("提交");
        }else {
            //已申请
            tvCommit.setVisibility(View.GONE);
            getData();
        }
        String str = "注意：发票信息提交后，便不可修改，请谨慎填写\n填写开票信息前，请仔细阅读《开票说明》";
        SpannableString string = new SpannableString(str);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FF0000"));
        string.setSpan(colorSpan, str.indexOf("《开票说明》"), str.indexOf("《开票说明》")+"《开票说明》".length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                AgreementWebViewActivity.launch(mActivity, Constant.H20);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);    //设置是否显示下划线
            }
        };
        string.setSpan(clickableSpan,str.indexOf("《开票说明》"),str.indexOf("《开票说明》")+"《开票说明》".length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvExplain.setText(string);
        tvExplain.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setEmailVisible(boolean visible){
      llEmail.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    @Override
    public void loadData() {

    }

    private void getData(){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order_no", order_no);
        new RxHttp<BaseResult<InvoiceBean>>().send(ApiManager.getService().invoice_info(hashMap),
                new Response<BaseResult<InvoiceBean>>( mActivity) {
                    @Override
                    public void onSuccess(BaseResult<InvoiceBean> result) {
                         InvoiceBean invoiceBean = result.data;
                         if (invoiceBean.invoice_state == 1){
                             tvType.setText("电子普通发票");
                         }else {
                             tvType.setText("纸质普通发票");
                         }
                         ivRight.setVisibility(View.GONE);
                         llType.setEnabled(false);
                        tvTypePerson.setEnabled(false);
                         tvTypeCompany.setEnabled(false);
                         etCompanyCode.setEnabled(false);
                         etCompanyName.setEnabled(false);
                         etEmail.setEnabled(false);
                         setInvoiceType(invoiceBean.invoice_type);
                        etCompanyCode.setText(invoiceBean.id_card);
                        etCompanyName.setText(invoiceBean.company_name);
                        etEmail.setText(invoiceBean.email);


                    }
                });
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_invoice;
    }




    private String invoice_type = "1";
    private void commit(){
        String company_name = etCompanyName.getText().toString().trim();
        String id_card = etCompanyCode.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("order_no", order_no);
        hashMap.put("invoice_state", tvType.getText().toString().equals("电子普通发票")?"1":"2");
        hashMap.put("invoice_type", invoice_type);
        if ("2".equals(invoice_type)){
            if (TextUtils.isEmpty(company_name)){
                tsg("请输入企业名称");
                return;
            } if (TextUtils.isEmpty(id_card)){
                tsg("请输入纳税人识别号");
                return;
            }
            hashMap.put("company_name", company_name);
            hashMap.put("id_card", id_card);
        }
        if (tvType.getText().toString().equals("电子普通发票")){
            if (TextUtils.isEmpty(email)){
                tsg("请输入邮箱地址");
                return;
            }
            hashMap.put("email", email);
        }
        new RxHttp<BaseResult>().send(ApiManager.getService().apply_invoice(hashMap),
                new Response<BaseResult>( mActivity) {
                    @Override
                    public void onSuccess(BaseResult result) {
                        CenterDialogUtil.showApplySuccess1(mContext,()->{
                            EventBus.getDefault().post(new OrderRefund());
                            finish();
                        });

                    }
                });

    }




    @Override
    public void permissionGranted() {
        PhotoUtils.getInstance().selectPic(mActivity);
    }






    @OnClick({R.id.ll_type, R.id.tv_type_person, R.id.tv_type_company, R.id.ll_email, R.id.tv_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_type:
                InvoiceDialogUtil.showSelectDialog(mActivity,"请选择发票类型",list,reason -> {
                    setEmailVisible("电子普通发票".equals(reason));
                    tvType.setText(reason);
                });
                break;
            case R.id.tv_type_person:
                setInvoiceType(1);
                break;
            case R.id.tv_type_company:
                setInvoiceType(2);
                break;
            case R.id.ll_email:
                break;
            case R.id.tv_commit:
                commit();

                break;
        }
    }

    private void setInvoiceType(int type){
        invoice_type = type+"";
        tvTypePerson.setTextColor(type == 1?getResources().getColor(R.color.baseColor,null)
                :getResources().getColor(R.color.grayText,null));
        tvTypeCompany.setTextColor(type == 2?getResources().getColor(R.color.baseColor,null)
                :getResources().getColor(R.color.grayText,null));

        tvTypePerson.setBackgroundResource(type == 1?R.drawable.shape_line_basecolor50_1px
                :R.drawable.shape_line_gray50);
        tvTypeCompany.setBackgroundResource(type == 2?R.drawable.shape_line_basecolor50_1px
                :R.drawable.shape_line_gray50);

        llCompanyInvoice.setVisibility(type == 1?View.GONE:View.VISIBLE);

    }
}
