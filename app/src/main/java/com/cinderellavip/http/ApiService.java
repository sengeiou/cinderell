package com.cinderellavip.http;


import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.BrandResult;
import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.bean.net.NetCityBean;
import com.cinderellavip.bean.net.ShopResult;
import com.cinderellavip.bean.net.UserInfo;
import com.cinderellavip.bean.net.goods.GoodsCommentResult;
import com.cinderellavip.bean.net.goods.GoodsResult;
import com.cinderellavip.bean.net.home.CateMoreList;
import com.cinderellavip.bean.net.home.HomeGoodsResult;
import com.cinderellavip.bean.net.home.ShopHomeResult;
import com.cinderellavip.bean.net.order.OrderSettleResult;

import java.util.TreeMap;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {

    /**
     * 登录
     * @param
     * @return
     */
    @POST(HttpUrl.login)
    @FormUrlEncoded
    Observable<BaseResult<UserInfo>>
    getLogin(@FieldMap TreeMap<String, String> map);
    @POST(HttpUrl.get_code)
    @FormUrlEncoded
    Observable<BaseResult>
    getCode(@FieldMap TreeMap<String, String> map);

    @POST(HttpUrl.code_login)
    @FormUrlEncoded
    Observable<BaseResult<UserInfo>>
    getCodeLogin(@FieldMap TreeMap<String, String> map);

    @POST(HttpUrl.register)
    @FormUrlEncoded
    Observable<BaseResult>
    getRegister(@FieldMap TreeMap<String, String> map);

    @POST(HttpUrl.forget_pass)
    @FormUrlEncoded
    Observable<BaseResult>
    getForgetPass(@FieldMap TreeMap<String, String> map);

    @GET(HttpUrl.home_category)
    Observable<BaseResult<HomeCategoryResult>>
    getHomeCategory();


    @GET(HttpUrl.home_index)
    Observable<BaseResult<ShopHomeResult>>
    getHome(@Query("first_category_id") String first_category_id);

    @GET(HttpUrl.home_goods)
    Observable<BaseResult<HomeGoodsResult>>
    getHomeGoods(@QueryMap TreeMap<String, String> map);


    @GET(HttpUrl.home_more_cate)
    Observable<BaseResult<ListResult<CateMoreList>>>
    getHomeMoreCate(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.home_more_goods)
    Observable<BaseResult<ListResult<HomeGoods>>>
    getHomeMoreGoods(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.search_goods)
    Observable<BaseResult<ListResult<HomeGoods>>>
    getSearchGoods(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.search_words)
    Observable<BaseResult<ListResult<String>>>
    getSearchWords();

    @GET(HttpUrl.goods_detail+"{id}")
    Observable<BaseResult<GoodsResult>>
    getGoodsDetail(@Path("id") String id);

    @GET(HttpUrl.goods_comment)
    Observable<BaseResult<GoodsCommentResult>>
    getGoodsComment(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.goods_coupons)
    Observable<BaseResult<GoodsResult>>
    getGoodsCoupons(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.coupons_receive)
    Observable<BaseResult>
    getReceiveCoupons(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.shop_detail)
    Observable<BaseResult<ShopResult>>
    getShopInfo(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.brand_detail)
    Observable<BaseResult<BrandResult>>
    getBrandInfo(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.goods_for_brand_and_shop)
    Observable<BaseResult<ListResult<HomeGoods>>>
    getBrandGoods(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.collect)
    Observable<BaseResult>
    getCollect(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.add_cart)
    Observable<BaseResult>
    getAddCart(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.settlement_product)
    Observable<BaseResult<OrderSettleResult>>
    getSettlementProduct(@QueryMap TreeMap<String, String> map);

    @GET(HttpUrl.address_list)
    Observable<BaseResult<ListResult<NetCityBean>>>
    getAddressList();

    @POST(HttpUrl.address_edit)
    @FormUrlEncoded
    Observable<BaseResult>
    getEditAddress(@FieldMap TreeMap<String, String> map);

    @GET(HttpUrl.address_delete)
    Observable<BaseResult>
    deleteAddress(@Query("id") String id);


}
