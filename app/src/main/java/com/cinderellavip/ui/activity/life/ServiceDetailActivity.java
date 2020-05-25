package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.ServiceProjectDetail;
import com.cinderellavip.global.CinderellApplication;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.ShareClickListener;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.BaseWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;

import java.util.TreeMap;

import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceDetailActivity extends BaseWebViewActivity {


    //服务项目和套餐ID
    private int id;
    public static void launch(Context from,int id) {
        Intent intent = new Intent(from, ServiceDetailActivity.class);
        intent.putExtra("id",id);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setLineVisibility();
        id = getIntent().getIntExtra("id",-1);
        setBackTitle("");
        setRightIcon(R.mipmap.share_service);

    }

    @Override
    public void loadData() {
        if (id == -1){
            tsg("获取id出错");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("project", id+"");
        hashMap.put("city",CinderellApplication.name+"");
        new RxHttp<BaseResult<ServiceProjectDetail>>().send(ApiManager.getService().serviceProjectAndPackageDetail(hashMap),
                new Response<BaseResult<ServiceProjectDetail>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ServiceProjectDetail> result) {
                        ServiceProjectDetail serviceProjectDetail = result.data;
                        setBackTitle(serviceProjectDetail.title);
                        loadData(serviceProjectDetail.content);
                    }

                    @Override
                    public void onErrorShow(String s) {
                        showError(s);
                    }
                });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_service_detail;
    }


    @Override
    public void initListener() {
        super.initListener();
        iv_right_icon.setOnClickListener(v -> {
            SecondDialogUtil.shareDialog(mContext, new ShareClickListener() {
                @Override
                public void onWeChatClick(Bitmap bitmap) {

                }

                @Override
                public void onWeChatCircleClick(Bitmap bitmap) {

                }
            });
        });
    }

    @OnClick({R.id.tv_service, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service:
                DialogUtil.showCallPhoneDialog(mActivity);
                break;
            case R.id.tv_buy:
//                BuyServiceActivity.launch(mActivity);
                break;
        }
    }
}
