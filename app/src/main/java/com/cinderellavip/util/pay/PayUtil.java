package com.cinderellavip.util.pay;

import android.app.Activity;

import com.cinderellavip.bean.net.order.GetPayResult;
import com.cinderellavip.bean.net.order.PayListener;

public class PayUtil {


    /**
     * 订单结算
     * @param context
     * @param payway
     * @param result
     * @param listener
     */
    public static void pay(Activity context, String payway, GetPayResult result, PayListener listener){
        switch (payway){
            case "1":
                AlipayUtils.getInstance().alipay(context, result.pay_string, new AlipayUtils.OnPayListener() {
                    @Override
                    public void onPaySuccess() {
                        listener.onResult(true);
                    }
                    @Override
                    public void onPayFail() {
                        listener.onResult(false);
                    }
                });
                break;
            case "2":
                WeChatUtils.getInstance(context).wechatPay(result.pay_info);
                break;
            case "3":
                listener.onResult(true);
                break;

        }

    }

    /**
     * 订单结算
     * @param context
     * @param payway
     * @param result
     * @param listener
     */
    public static void payLifeOrder(Activity context, String payway, GetPayResult result, PayListener listener){
        switch (payway){
            case "1":
                AlipayUtils.getInstance().alipay(context, result.pay_string, new AlipayUtils.OnPayListener() {
                    @Override
                    public void onPaySuccess() {
                        listener.onResult(true);
                    }
                    @Override
                    public void onPayFail() {
                        listener.onResult(false);
                    }
                });
                break;
            case "2":
                WeChatUtils.getInstance(context).wechatPay(result.pay_info);
                break;
            case "0":
                listener.onResult(true);
                break;

        }

    }




}
