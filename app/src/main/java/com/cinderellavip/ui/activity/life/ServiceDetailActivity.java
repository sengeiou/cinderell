package com.cinderellavip.ui.activity.life;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.cinderellavip.R;
import com.cinderellavip.listener.ShareClickListener;
import com.cinderellavip.toast.DialogUtil;
import com.cinderellavip.toast.SecondDialogUtil;
import com.cinderellavip.ui.BaseWebViewActivity;

import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/8.
 */
public class ServiceDetailActivity extends BaseWebViewActivity {


    public static void launch(Context from) {
        Intent intent = new Intent(from, ServiceDetailActivity.class);
        from.startActivity(intent);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackTitle("物品整理服务");
        setRightIcon(R.mipmap.share_service);

    }

    @Override
    public void loadData() {
        loadUrl(url);

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
                BuyServiceActivity.launch(mActivity);
                break;
        }
    }
}
