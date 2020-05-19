package com.cinderellavip.http;

import android.text.TextUtils;

import com.cinderellavip.global.GlobalParam;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class CommonInterceptor implements Interceptor {

    private static TreeMap<String, String> commonParams;

    public synchronized static void setCommonParam(Map<String, String> commonParams) {
        if (commonParams != null) {
            if (CommonInterceptor.commonParams != null) {
                CommonInterceptor.commonParams.clear();
            } else {
                CommonInterceptor.commonParams = new TreeMap<>();
            }
            for (String paramKey : commonParams.keySet()) {
                CommonInterceptor.commonParams.put(paramKey, commonParams.get(paramKey));
            }
        }
    }

    public synchronized static void updateOrInsertCommonParam(@NonNull String paramKey, @NonNull String paramValue) {
        if (commonParams == null) {
            commonParams = new TreeMap<>();
        }
        commonParams.put(paramKey, paramValue);
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {
        Request request = rebuildRequest(chain.request());
        Response response = chain.proceed(request);
        // 输出返回结果
        try {
            Charset charset;
            charset = Charset.forName("UTF-8");
            ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
            Reader jsonReader = new InputStreamReader(responseBody.byteStream(), charset);
            BufferedReader reader = new BufferedReader(jsonReader);
            StringBuilder sbJson = new StringBuilder();
            String line = reader.readLine();
            do {
                sbJson.append(line);
                line = reader.readLine();
            } while (line != null);
//            LogUtil.e("response: " + sbJson.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        saveCookies(response, request.url().toString());
        return response;
    }


    public static byte[] toByteArray(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        InputStream inputStream = buffer.inputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bufferWrite = new byte[4096];
        int n;
        while (-1 != (n = inputStream.read(bufferWrite))) {
            output.write(bufferWrite, 0, n);
        }
        return output.toByteArray();
    }

    private Request rebuildRequest(Request request) throws IOException {
        Request newRequest;
        if ("POST".equals(request.method())) {
            newRequest = rebuildPostRequest(request);
        } else if ("GET".equals(request.method())) {
            newRequest = rebuildGetRequest(request);
        } else {
            newRequest = request;
        }
//        LogUtil.e("requestUrl: " + newRequest.url().toString());
        return newRequest;
    }

    /**
     * 对post请求添加统一参数
     */
    private Request rebuildPostRequest(Request request) {
            if (request.body() instanceof FormBody) {
                TreeMap<String, String> signParams = new TreeMap<>(); // 假设你的项目需要对参数进行签名
                FormBody formBody = (FormBody) request.body();
                // 先复制原来的参数
                for (int i = 0; i < formBody.size(); i++) {
                    try {
                        signParams.put(formBody.encodedName(i), URLDecoder.decode(formBody.encodedValue(i), "UTF-8"));
                    }catch (Exception e){

                    }

//                    LogUtil.e( formBody.encodedName(i)+"==="+formBody.encodedValue(i));
                }

                String time = "" + System.currentTimeMillis() / 1000;
                String userToken = GlobalParam.getUserToken();

                request = request.newBuilder()
                        .addHeader("X-Timestamp", time)
                        .addHeader("Accept","application/json")
                        .addHeader("X-User-Token",userToken)
                        .addHeader("X-Sign",SignUtil.getMd5(signParams,time))
                        .build();
                return request;
            }else {
                return request;
            }
    }

    /**
     * 获取常规post请求参数
     */
    private String getParamContent(RequestBody body) throws IOException {
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

    /**
     * 对get请求做统一参数处理
     */
    private Request rebuildGetRequest(Request request) {
        String url = request.url().toString();
        int separatorIndex = url.lastIndexOf("?");

//        LogUtil.e(url);
        System.out.println(url);
        StringBuilder sb = new StringBuilder(url);
        String string = sb.toString();
        Request.Builder requestBuilder = request.newBuilder();
        String time = "" + System.currentTimeMillis() / 1000;
        requestBuilder.addHeader("X-Timestamp", time);
        requestBuilder.addHeader("Accept","application/json");
        String userToken = GlobalParam.getUserToken();
        if (!TextUtils.isEmpty(userToken)){
            requestBuilder.addHeader("X-User-Token",userToken);
        }
        if (separatorIndex == -1) {
            String s = "secret=241cd2aa2aae01cd2&"+"timestamp="+time;
            requestBuilder.addHeader("X-Sign",SignUtil.getMd5(s));
        }else {
//            String s = "secret=241cd2aa2aae01cd2&"+"timestamp="+time;
            String substring = url.substring(separatorIndex + 1);
            String replaceAll = substring.replaceAll("&", "=");
            String[] split = replaceAll.split("=");
            TreeMap<String, String> keys = new TreeMap<>();
            for (int i = 0;i<split.length;){
                try {
                    keys.put(split[i],URLDecoder.decode(split[i+1], "UTF-8"));
                }catch (Exception e){

                }
//                System.out.println("参数 key = "+split[i]+",值"+split[i+1]);
                i = i+2;
            }
            requestBuilder.addHeader("X-Sign",SignUtil.getMd5(keys,time));
//            System.out.println("签名"+SignUtil.getMd5(keys,time));
//            requestBuilder.addHeader("X-Sign",
//                    SignUtil.getMd5(url.substring(separatorIndex+1,url.length())+"&"+s));
        }

        return requestBuilder.url(string).build();
    }


}
