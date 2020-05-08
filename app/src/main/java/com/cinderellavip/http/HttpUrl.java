package com.cinderellavip.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {


    String server_url = "https://api.huiguniangvip.com/";
    String image_url = server_url+"gp/profile/";

    String login = "api/store/login";  //用户名登录
    String get_code = "api/store/sms/send";  //获取验证码
    String code_login = "api/store/login/fast";  //短信验证码登录
    String register = "api/store/register";  //注册
    String forget_pass = "api/store/password/forget";  //忘记密码



}
