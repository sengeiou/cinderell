package com.cinderellavip.util.pay;

import android.content.Context;

import com.cinderellavip.bean.net.order.PayInfo;
import com.cinderellavip.global.CinderellaApplication;
import com.cinderellavip.global.Constant;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tozzais.baselibrary.util.toast.ToastCommom;

/**
 * Created by jumpbox on 16/6/2.
 */
public class WeChatUtils {
    private static WeChatUtils weChatUtils;
    private static IWXAPI api;


    public static WeChatUtils getInstance(Context context) {
        if (weChatUtils == null) {
            weChatUtils = new WeChatUtils();
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, null);
            api.registerApp(Constant.WX_APPID);
        }
        return weChatUtils;
    }

    public void wechatPay(PayInfo info) {
        if (!api.isWXAppInstalled()) {
            ToastCommom.createToastConfig().ToastShow(CinderellaApplication.mContext, "您还没有安装微信");
            return;
        }
        PayReq req = new PayReq();
        req.appId = info.appid;
        req.partnerId = info.partnerid;
        req.prepayId = info.prepayid;
        req.nonceStr = info.noncestr;
        req.timeStamp = info.timestamp;
        req.packageValue = info.pack_age;
        req.sign = info.sign;
        api.sendReq(req);


    }


    public void release() {
        api = null;
        weChatUtils = null;
    }

}
