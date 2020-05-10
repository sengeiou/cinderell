package com.cinderellavip.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {


    String server_url = "https://api.huiguniangvip.com/";

    String login = "api/store/login";  //用户名登录
    String get_code = "api/store/sms/send";  //获取验证码
    String code_login = "api/store/login/fast";  //短信验证码登录
    String register = "api/store/register";  //注册
    String forget_pass = "api/store/password/forget";  //忘记密码


    String home_category = "api/store/categories/first";  //首页分类
    String home_index = "api/store/index";  //首页接口
    String home_goods = "api/store/index/products";  //首页商品
    String home_more_cate = "api/store/more-cate";
    String home_more_goods = "api/store/cate-products";//根据三级分类获取商品



}
