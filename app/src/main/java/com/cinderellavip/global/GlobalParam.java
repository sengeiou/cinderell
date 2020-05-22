package com.cinderellavip.global;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.cinderellavip.bean.net.UserInfo;
import com.cinderellavip.bean.net.mine.MineInfo;
import com.cinderellavip.ui.activity.account.LoginActivity;
import com.google.gson.Gson;
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

    public static boolean getUserLogin(Context context) {
        boolean isLogin = SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_login, false);
        if (!isLogin){
            LoginActivity.launch((Activity) context,true);
        }
        return isLogin;
    }

    //存 用户的token
    public static void setUserId(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.user_id, userid);
    }
    //取 用户的用户的token
    public static String getUserId() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.user_id,"");
    }



    //存 用户的推荐码
    public static void setRecommendCode(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.user_recommend_code, userid);
    }
    //取 用户的用户的推荐码
    public static String getRecommendCode() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.user_recommend_code,"");
    }
    //存 用户是够是vip
    public static void setIsVip(boolean userid) {
        SharedPreferencesUtil.saveBooleanData(CinderellApplication.mContext, Constant.user_is_vip, userid);
    }
    //取 用户是够是vip
    public static boolean getIsVip() {
        return SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_is_vip,false);
    }


    //存 用户 信息
    public static void setUserInfo(UserInfo userInfo) {
        setUserLogin(true);
       setUserToken(userInfo.token);
       setUserId(userInfo.user_id+"");
       setIsVip(userInfo.type == 1);
       setRecommendCode(userInfo.invite_code);
    }

    //存 用户 信息
    public static void exitLogin() {
        setUserLogin(false);
        setUserToken("");
        setUserId("");
        setIsVip(false);
        setRecommendCode("");
    }


    //存 用户的token
    public static void setSearch(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.search, userid);
    }
    //取 用户的用户的token
    public static String getSearch() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.search,"");
    }

    //存 生活服务搜索记录
    public static void setLifeSearch(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.search_life, userid);
    }
    //取  生活服务搜索记录
    public static String getLifeSearch() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.search_life,"");
    }

    //存 帖子搜索记录
    public static void setSearchPost(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.search_post, userid);
    }
    //取 帖子搜索记录
    public static String getSearchPost() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.search_post,"");
    }

    //存 话题搜索记录
    public static void setSearchTopic(String userid) {
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.search_topic, userid);
    }
    //取 话题搜索记录
    public static String getSearchTopic() {
        return SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.search_topic,"");
    }



    //登录成功是否直接返回 用户没登录的情况 操作之后 返回当前界面
    public static void setLoginFinish(boolean loginFinish) {
        SharedPreferencesUtil.saveBooleanData(CinderellApplication.mContext, Constant.user_login_finish, loginFinish);
    }
    public static boolean getLoginFinish() {
        return SharedPreferencesUtil.getBooleanData(CinderellApplication.mContext, Constant.user_login_finish,false);
    }


    public static void setUserBean(MineInfo userInfo) {
        Gson gson = new Gson();
        SharedPreferencesUtil.saveStringData(CinderellApplication.mContext, Constant.user_bean_string, gson.toJson(userInfo));
    }
    public static MineInfo getUserBean() {
        String data = SharedPreferencesUtil.getStringData(CinderellApplication.mContext, Constant.user_bean_string, "");
        if (TextUtils.isEmpty(data)){
            return null;
        }
        return new Gson().fromJson(data,MineInfo.class);
    }

}
