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
    String home_more_cate = "api/store/more-cate"; //更多分类
    String home_more_goods = "api/store/cate-products";//根据三级分类获取商品
    String search_words = "api/store/search/hot-words";//热搜词汇
    String search_goods = "api/store/search-products";//搜索商品
    String goods_detail = "api/store/product/info/";//商品详情
    String goods_comment = "api/store/product/comments";//商品评价
    String goods_coupons = "api/store/product/coupons";//商品优惠券
    String coupons_receive = "api/store/coupon/receive";//商品优惠券


}
