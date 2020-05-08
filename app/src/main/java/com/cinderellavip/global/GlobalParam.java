package com.cinderellavip.global;


import com.tozzais.baselibrary.util.SharedPreferencesUtil;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {





    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(CinderellApplication.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_first_use,false);
    }


    //存 用户的token
    public static void setUserToken(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.user_token, userid);
    }
    //取 用户的用户的token
    public static String getUserToken() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.user_token,"");
    }

    //是否登录
    public static void setUserLogin(boolean userid) {
        SharedPreferencesUtil.saveBooleanData(CinderellApplication.mContext, Constant.user_login, userid);
    }
    public static boolean getUserLogin() {
        return SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_login,false);
    }


}
