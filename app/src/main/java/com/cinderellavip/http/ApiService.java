package com.cinderellavip.http;


import com.cinderellavip.bean.net.UserInfo;

import java.util.TreeMap;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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

}
