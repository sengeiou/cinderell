package com.cinderellavip.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.utils.LogUtils;
import com.sobot.chat.utils.ZhiChiConstant;

/**
 * 点击通知以后发出的广播接收者
 */
public class SobotNotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ZhiChiConstant.SOBOT_NOTIFICATION_CLICK.equals(intent.getAction())){
            SobotUtils.start(context);

        }
    }
}