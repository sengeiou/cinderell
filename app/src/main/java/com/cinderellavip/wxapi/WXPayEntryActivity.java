package com.cinderellavip.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cinderellavip.global.Constant;
import com.cinderellavip.util.pay.PayResultEvent;
import com.cinderellavip.util.pay.WeChatUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tozzais.baselibrary.util.log.LogUtil;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("支付","打开了onNewIntent" );
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("支付",resp.getType()+"打22开了"+resp.errCode );
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtil.e(resp.errCode + "," + resp.errStr);
            finish();
            PayResultEvent resultEvent = new PayResultEvent();
            LogUtil.e(resp.errCode+"");
            switch (resp.errCode) {

                case 0:
                    //支付成功
                    this.finish();
                    resultEvent.status = 0;
                    break;
                case -1:
                    //失败
                    this.finish();
                    resultEvent.status = 1;
                    break;
                case -2:
                    //取消
                    this.finish();
                    resultEvent.status = 2;
                    break;


            }
            EventBus.getDefault().post(resultEvent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("支付","打开了onCreate" );

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID);
        api.handleIntent(getIntent(), this);
    }


}