package com.cinderellavip.ui.activity.life;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.life.ServiceProjectDetail;
import com.cinderellavip.bean.request.LifePreOrder;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.listener.ShareClickListener;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.BaseWebViewActivity;
import com.tozzais.baselibrary.http.RxHttp;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.TreeMap;

import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceDetailActivity extends BaseWebViewActivity {


    //服务项目和套餐ID
    private int id;
    private String city;
    public static void launch(Context from,int id,String city) {
        Intent intent = new Intent(from, ServiceDetailActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("city",city);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setLineVisibility();
        id = getIntent().getIntExtra("id",-1);
        city = getIntent().getStringExtra("city");
        setBackTitle("");
        setRightIcon(R.mipmap.share_service);

    }

    ServiceProjectDetail serviceProjectDetail;
    @Override
    public void loadData() {
        if (id == -1){
            tsg("获取id出错");
            return;
        }
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("project", id+"");
        hashMap.put("city",city);
        new RxHttp<BaseResult<ServiceProjectDetail>>().send(ApiManager.getService().serviceProjectAndPackageDetail(hashMap),
                new Response<BaseResult<ServiceProjectDetail>>(isLoad,mActivity) {
                    @Override
                    public void onSuccess(BaseResult<ServiceProjectDetail> result) {
                        showContent();
                        serviceProjectDetail = result.data;
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
            if (serviceProjectDetail != null)
            SecondDialogUtil.shareDialog(mContext,serviceProjectDetail, new ShareClickListener() {
                @Override
                public void onWeChatClick(Bitmap bitmap) {
                    shareImage(SHARE_MEDIA.WEIXIN, bitmap);
                }
                @Override
                public void onWeChatCircleClick(Bitmap bitmap) {
                    shareImage(SHARE_MEDIA.WEIXIN_CIRCLE, bitmap);
                }
            });
        });
    }

    public void shareImage(SHARE_MEDIA share_media, Bitmap bitmap) {
        UMImage imagelocal = new UMImage(mContext, bitmap);
        imagelocal.setThumb(new UMImage(mContext, bitmap));
        new ShareAction((Activity) mContext).withMedia(imagelocal)
                .setPlatform(share_media)
                .setCallback(null).share();
    }



    @OnClick({R.id.tv_service, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_service:
                DialogUtil.showCallPhoneDialog(mActivity,3);
                break;
            case R.id.tv_buy:
                if (id != -1){
                    LifePreOrder lifePreOrder = new LifePreOrder();
                    lifePreOrder.type = LifePreOrder.SHORT;
                    lifePreOrder.project = id;
                    lifePreOrder.city = city;
                    BuyServiceActivity.launch(mActivity,lifePreOrder);
                }

                break;
        }
    }
}
