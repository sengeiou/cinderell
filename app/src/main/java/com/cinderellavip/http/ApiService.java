package com.cinderellavip.http;



import java.util.TreeMap;

import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {

    //登录
    @POST(HttpUrl.login)
    Observable<BaseResult>
    getlogin(@QueryMap TreeMap<String, String> map);


}
