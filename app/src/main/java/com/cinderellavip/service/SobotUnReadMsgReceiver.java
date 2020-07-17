package com.cinderellavip.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cinderellavip.global.GlobalParam;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.utils.LogUtils;
import com.sobot.chat.utils.ZhiChiConstant;

import org.greenrobot.eventbus.EventBus;

/**
 * 获取未读消息的广播接收者
 */
public class SobotUnReadMsgReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ZhiChiConstant.sobot_unreadCountBrocast.equals(intent.getAction())){
            int noReadNum = intent.getIntExtra("noReadCount", 0);
            String content = intent.getStringExtra("content");
            EventBus.getDefault().post(new ServiceMsg(noReadNum,content));
            LogUtils.i("未读消息数是：" + noReadNum + "\t" + "最新一条消息内容是：" + content);
//            ZCSobotApi.getUnReadMessage(mActivity, GlobalParam.getUserId())
        }

    }
}