package com.cinderellavip.global;

import android.os.Environment;

import com.cinderellavip.http.HttpUrl;

/**
 * 全部的常量类。
 * Created by Administrator on 2017/4/15.
 */

public class Constant {

    //图片地址
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/hk_fight/image";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/hk_fight";
    public static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/hk_fight";


    public static String user_login = "cinder_user_login";
    public static String user_id = "cinder_user_id";
    public static String user_token = "cinder_user_token";
    public static String user_first_use = "cinder_user_first_use";

    public static String user_login_finish = "cinder_user_login_finish";

    public static String search = "cinder_user_search";




    //微信相关
    public static String SMALL_APPLICATION_ID = "gh_269043eeeee4";
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
