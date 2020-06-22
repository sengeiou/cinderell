package com.cinderellavip.global;

import android.os.Environment;

import com.cinderellavip.http.HttpUrl;

/**
 * 全部的常量类。
 * Created by Administrator on 2017/4/15.
 */

public class Constant {

    //图片地址
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cinderellavip/image";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/cinderellavip";
    public static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/cinderellavip";


    public static String user_login = "cinder_user_login";
    public static String user_id = "cinder_user_id";
    public static String user_token = "cinder_user_token";
    public static String user_first_use = "cinder_user_first_use";
    public static String user_login_finish = "cinder_user_login_finish";
    public static String user_recommend_code = "cinder_user_recommend_code";
    public static String user_is_vip = "cinder_is_vip";

    public static String user_bean_string = "cinder_user_login_bean";
    public static String user_bean_phone = "cinder_phone_bean";

    public static String search = "cinder_user_search";
    public static String search_post = "cinder_user_search_post";
    public static String search_topic = "cinder_user_search_topic";
    public static String search_life = "cinder_user_search_life";






    //微信相关
    public static String SMALL_APPLICATION_ID = "gh_659404972ef7";
    public static String WX_APPID = "wx85d0dcba9bb8a9dc";
    public static String WX_APP_SECRET = "4c5cee09aad0705dcf43e9dc1805c515";

    /**
     * h5相关
     *
     */
    //服务协议
    public static String H5_SERVICE = HttpUrl.server_url+"api/user/agreements/1";
    //隐私协议
    public static String H5_PRIVACY = HttpUrl.server_url+"api/user/agreements/2";
    //关于我们
    public static String H5_ABOUT_US = HttpUrl.server_url+"api/user/agreements/4";

    public static String H3 = HttpUrl.server_url+"api/user/agreements/3";
    public static String H5 = HttpUrl.server_url+"api/user/agreements/5";
    public static String H6 = HttpUrl.server_url+"api/user/agreements/6";
    public static String H7 = HttpUrl.server_url+"api/user/agreements/7";
    public static String H8 = HttpUrl.server_url+"api/user/agreements/8";
    public static String H9 = HttpUrl.server_url+"api/user/agreements/9";
    public static String H10 = HttpUrl.server_url+"api/user/agreements/10";
    public static String H11 = HttpUrl.server_url+"api/user/agreements/11";
    public static String H12 = HttpUrl.server_url+"api/user/agreements/12";
    public static String H13 = HttpUrl.server_url+"api/user/agreements/13";
    public static String H14 = HttpUrl.server_url+"api/user/agreements/14";
    public static String H15 = HttpUrl.server_url+"api/user/agreements/15";
    public static String H16 = HttpUrl.server_url+"api/user/agreements/16";
    public static String H17 = HttpUrl.server_url+"api/user/agreements/17";
    public static String H18 = HttpUrl.server_url+"api/user/agreements/18";
    public static String H19 = "http://h5.huiguniangvip.com/#/apply/0/";
    public static String H20 = HttpUrl.server_url+"api/user/agreements/19";

    public static final int ALL = 0;
    public static final int CANCEL = 1;
    public static final int UNPAY = 2;
    public static final int STOCKING = 3;
    public static final int UNSEND = 4;
    public static final int UNRECEIVE = 5;
    public static final int EVALUATION = 6;
    public static final int FINISH = 7;
    public static final int RETURNING = 8;
    public static final int RETURNED = 9;


    public static final int MENTION = 0; //自提
    public static final int LOGISTICS = 1; //物流


}
