package com.cinderellavip.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {

    String server_url = "https://api.huiguniangvip.com/";
    //上传图片
    String upload = "api/store/upload/image";  //上传图片
    String uploads = "api/store/upload/images";  //上传图片
    String get_code = "api/store/sms/send";  //获取验证码
    String get_qr_code = "api/store/applets/qr-code";  //获取小程序码
    String get_phone = "api/store/platform/customer";  //获取客服电话
    //登录注册
    String login = "api/store/login";  //用户名登录
    String code_login = "api/store/login/fast";  //短信验证码登录
    String register = "api/store/register";  //注册
    String forget_pass = "api/store/password/forget";  //忘记密码
    String is_bind = "api/store/union_id/is_register";  //判断第三方是否绑定
    String bind = "api/store/login/applets/three";  //第三方登录之绑定手机号
    //首页
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
    String coupons_receive = "api/store/coupon/receive";//领取优惠券
    String brand_detail = "api/store/brand/info";//品牌详情
    String shop_detail = "api/store/store/info";//店铺详情
    String goods_for_brand_and_shop = "api/store/product/list/by-store-brand";//品牌 店铺 商品
    String collect = "api/store/collect/third";//收藏
    String banner_detail = "api/store/banner/info";//轮播详情
    String store_category = "api/store/store/categories";//轮播详情
    //订单
    String add_cart = "api/store/cart/add";//加入购物车
    String modify_cart_number = "api/store/cart/num";//修改购物车数量
    String delete_cart = "api/store/cart/delete";//删除购物车
    String order_pay = "api/store/order/pay";//订单支付
    String order_pay_likes = "api/store/order/likes";//订单支付成功时获取猜你喜欢的商品
    String settlement_product = "api/store/settlement/product";//直接购买结算页面
    String create_order_product = "api/store/order/create-product";//直接购买结算生成订单
    String order_list = "api/store/order/list";//我的订单列表
    String order_info = "api/store/order/info/";//订单详情
    String order_cancel = "api/store/order/cancel/";//取消订单
    String send_order_cancel = "api/store/order/cancel";//取消订单
    String order_receipt = "api/store/order/receipt/";//确认订单
    String order_comment = "api/store/order/commit";//订单评价
    String order_logistics = "api/store/order/logistics/";//订单物流
    String cart_list = "api/store/cart/list-new";//购物车列表
    String settlement_cart = "api/store/settlement/cart-new";//购物车结算
    String create_order_cart = "api/store/order/create-cart-new";//生成订单（购物车）新版
    String coupons_settlement = "api/store/settlement/coupons";//结算时获取可用优惠券列表
    //收货地址
    String address_list = "api/store/address/list";//收货地址列表
    String address_delete = "api/store/address/delete";//删除收货地址
    String address_edit = "api/store/address/edit";//编辑收货地址
    //个人中心
    String mine_potato = "api/store/my/gray-bean";//我的灰豆
    String message_list = "api/store/my/message/list";//消息列表
    String message_center = "api/store/my/message/center";//消息中心
    String shield_users = "api/store/my/shield_users";//我拉黑的用户
    String apply_supplier_result = "api/store/my/supplier/result";//获取申请成为供应商状态
    String apply_supplier = "api/store/my/apply/supplier";//申请成为供应商
    String apply_supplier_cate = "api/store/my/apply/supplier/cate";//申请供应商获取经营类别
    String ranking_history = "api/store/team/ranking/history";//月冠历史排行月份获取
    String ranking = "api/store/team/ranking";//月冠排行
    String mine_integral = "api/store/my/integral";//我的小金库
    String sign = "api/store/my/sign";//签到
    String ming_inviter_number = "api/store/my/inviter/num";//我邀请的好友
    String ming_inviter = "api/store/my/inviter";//我邀请的好友
    String apply_vip = "api/store/my/apply-vip";//申请成为灰姑娘
    String withdrawal = "api/store/my/withdrawal";//我的提现记录
    String withdrawal_apply = "api/store/my/withdraw/apply";//申请提现
    String collects = "api/store/my/collects";//我的收藏
    String balance = "api/store/my/balance";//我的钱包
    String coupons_center = "api/store/coupon/center";//领券中心
    String coupons_mine = "api/store/my/coupons";//我的优惠券
    String password_reset = "api/store/password/reset";//修改登录密码
    String update_info = "api/store/my/info-update";//修改个人信息
    String mine_center = "api/store/my/center";//个人中心首页
    String score_withdraw_explain = "api/store/my/integral/info";//积分提现手续费获取
    String score_withdraw_history = "api/store/my/withdrawal/int";//我的提现记录
    String score_withdraw_apply = "api/store/my/withdraw/apply/int";//积分提现申请
    //拼团
    String group_order_info = "api/store/group/order-info/";//我的团购订单详情
    String group_order_list = "api/store/group/my-orders";//我的团购订单列表
    String group_order_pay = "api/store/group/order/pay";//支付拼团订单
    String group_order_create = "api/store/group/order/create";//拼团创建订单
    String group_order_settle = "api/store/settlement/group";//拼团结算
    //退款
    String refund_info = "api/store/refund/info";//退款订单详情
    String refund_order_list = "api/store/refund/list";//退款订单列表
    String refund_commit = "api/store/refund/submit";//提交退款申请
    String refund_reason = "api/store/refund/reasons";//退款理由
    //发现
    String discuss_shield = "api/store/discuss/shield";//拉黑、解除拉黑用户
    String discuss_report = "api/store/discuss/report";//举报用户、帖子、话题
    String discuss_collects = "api/store/discuss/collects";//关注
    String discuss_search = "api/store/discuss/search";//搜索帖子、话题
    String discuss_info = "api/store/discuss/info";//帖子详情
    String discuss_reply = "api/store/discuss/reply";//帖子回复
    String discuss_comment = "api/store/discuss/comment";//评论帖子
    String topic_info = "api/store/topic/info";//话题详情
    String discuss_index = "api/store/discuss/index";//发现首页
    String discuss_release = "api/store/discuss/release/discuss";//发布帖子
    String discuss_search_product = "api/store/discuss/search-product";//发布帖子获取商品列表
    String discuss_search_topic = "api/store/discuss/search-topic";//发布帖子获取话题列表
    String topic_release = "api/store/discuss/release/topic";//发布话题
    //生活服务
    String life_home = "api/service/home/get";//首页
    String life_searchList = "api/service/search/searchList";//搜索列表
    String life_search_popular = "api/service/search/popular";//热门搜索词
    String life_long_pay = "api/service/LongPay/BalancePay";//支付
    String life_pay = "api/service/pay/BalancePay";//支付
    String life_long_cancel = "api/service/long/orderCancel";//长期订单取消
    String life_long_order_info = "api/service/long/orderDetails";//长期订单详情
    String life_long_order_coupons = "api/service/long/couponList";//长期订单优惠券
    String life_long_order_prepay = "api/service/long/Prepayment";//长期订单预支付
    String life_long_order_confirm_price = "api/service/long/ConfirmPricing";//长期订单 确认定价
    String life_long_order_list = "api/service/long/OrderLongList";//长期订单 列表
    String life_long_order_confirm_order = "api/service/long/ConfirmOrder";//长期订单 确认下单
    String life_long_order_pre_order = "api/service/long/PreOrder";//长期订单 预下单（没用）
    String life_order_comment = "api/service/evaluation/CommentPost";//评价提交-短期项目
    String life_order_comment_can = "api/service/evaluation/orderCanComment";//订单是否可以评价
    String life_order_comment_label = "api/service/evaluation/getLabel";//评价评价标签
    String life_direct_list_map = "api/service/justwaiter/get_map_list";//直约服务员地图列表数据
    String life_direct_list = "api/service/justwaiter/get_list";//直约服务员列表
    String life_direct_time = "api/service/justwergh/waitertime";//直约服务员可选择时间
    String life_direct_order_confirm = "api/service/justwergh/Confirm_order";//直约确认下单
    String life_direct_order_coupins = "api/service/justwergh/couponList";//获取直约可用优惠券
    String life_direct_order_pre = "api/service/justwergh/Pre_order";//直约预下单
    String life_project_detail = "api/service/servicewaiter/projectDet";//服务项目详情
    String life_project_comment = "api/service/servicewaiter/commentlist";//评论列表
    String life_service_person_detail = "api/service/servicewaiter/getDetails";//服务人员详情
    String life_order_cancel = "api/service/life/lifeOrderCancel";//生活订单-取消订单
    String life_order_info = "api/service/life/lifeorderdetails";//生活订单-详情
    String life_order_category = "api/service/life/lifeordersort";//生活订单-可选择分类
    String life_order_list = "api/service/life/lifeorderlist";//生活订单-列表
    String life_order_time = "api/service/order/selectTime";//服务项目-选择时间-短期项目
    String life_order_pay = "api/service/order/orderpay";//订单支付-- 短期项目 （没用）
    String life_order_pay_info = "api/service/order/tobepaid";//更具id获取订单待支付信息-- 短期项目
    String life_order_confirm_order = "api/service/order/confirm_order";//服务项目--确认下单-- 短期项目
    String life_order_pre_order = "api/service/order/pre_order";//服务项目--预下单  -- 短期项目
    String life_address_info = "api/service/address/getfirst";//获取一条地址信息
    String life_address_list = "api/service/address/getlist";//获取一条地址信息
    String life_address_edit = "api/service/address/addedit";//新增与修改地址
    String life_coupons = "api/service/coupon/meetsthe";//获取当前项目可选择的优惠券
    String life_coupons_receive = "api/service/coupon/receive";//领取优惠券
    String life_coupons_receive_condition = "api/service/coupon/getlist";//获取符合条件的优惠券
    String life_direct_cate = "api/service/service/Direct_map";//直约页面显示的分类列表
    String life_OneProjectList = "api/service/service/OneProjectList";//获取一级分类与下级的项目
    String life_oneList = "api/service/service/oneList";//获取一级分类
    String life_details = "api/service/service/details";//服务项目套餐详情
    String life_category = "api/service/service/category";//服务分类详情
    String life_checklist = "api/service/service/checklist";//服务清单



}
