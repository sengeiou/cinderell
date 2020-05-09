package com.cinderellavip.http;


import com.cinderellavip.bean.net.HomeCategoryResult;
import com.cinderellavip.bean.net.UserInfo;
import com.cinderellavip.bean.net.home.ShopHomeResult;

import java.util.TreeMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
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

}
