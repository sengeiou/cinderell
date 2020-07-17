package com.cinderellavip.service;

import android.content.Context;
import android.text.TextUtils;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.global.Constant;
import com.cinderellavip.global.GlobalParam;
import com.cinderellavip.http.ApiManager;
import com.cinderellavip.http.BaseResult;
import com.cinderellavip.http.Response;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.ui.activity.home.ShareActivity;
import com.sobot.chat.MarkConfig;
import com.sobot.chat.SobotApi;
import com.sobot.chat.ZCSobotApi;
import com.sobot.chat.api.enumtype.SobotChatStatusMode;
import com.sobot.chat.api.enumtype.SobotChatTitleDisplayMode;
import com.sobot.chat.api.model.ConsultingContent;
import com.sobot.chat.api.model.Information;
import com.sobot.chat.listener.NewHyperlinkListener;
import com.sobot.chat.listener.SobotChatStatusListener;
import com.sobot.chat.utils.SharedPreferencesUtil;
import com.sobot.chat.utils.ToastUtil;
import com.tozzais.baselibrary.http.RxHttp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.sobot.chat.api.apiUtils.SobotApp.getApplicationContext;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SobotUtils {

    private static int enumType = 0;//0 默认,  1  自定义,  2  公司name;
    private static long sobot_show_history_ruler = 0;//显示多少分钟内的历史记录  10分钟 -24小时

    public static void start(Context context) {
        if (context == null) {
            return;
        }
        MineInfo userBean = GlobalParam.getUserBean();
        if (userBean == null){
            new RxHttp<BaseResult<MineInfo>>().send(ApiManager.getService().getMineInfo(),
                    new Response<BaseResult<MineInfo>>(true,context) {
                        @Override
                        public void onSuccess(BaseResult<MineInfo> result) {
                            GlobalParam.setUserBean(result.data);
                            MineInfo userBean = result.data;
                            Information info = new Information();
                            info.setApp_key(Constant.KEY_SERVICE);
                            info.setPartnerid(GlobalParam.getUserId());
                            info.setUser_nick(userBean.username);
                            info.setUser_tels(userBean.mobile);
                            info.setFace(userBean.user_avatar);
                            ZCSobotApi.openZCChat(context, info);

                        }
                    });
        }else {
            Information info = new Information();
            info.setApp_key(Constant.KEY_SERVICE);
            info.setPartnerid(GlobalParam.getUserId());
            info.setUser_nick(userBean.username);
            info.setUser_tels(userBean.mobile);
            info.setFace(userBean.user_avatar);
            ZCSobotApi.openZCChat(context, info);
        }



    }
}