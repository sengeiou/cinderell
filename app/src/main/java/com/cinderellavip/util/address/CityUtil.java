package com.cinderellavip.util.address;

import android.content.Context;

import com.cinderellavip.util.lifeaddress.LifeAddress;
import com.cinderellavip.util.lifeaddress.LifeAddressResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CityUtil {
    public static String convertStreamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream) );
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
               inputStream.close();
               bufferedReader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
    public static ArrayList<LocalCity> convertStream(Context context){
        InputStream inputStream = null;
        ArrayList<LocalCity> beans = new ArrayList<>();
        try {
            inputStream = context.getAssets().open("city.json");
            String str = convertStreamToString(inputStream);
            Gson gson = new Gson();

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonElements = jsonParser.parse(str).getAsJsonArray();//获取JsonArray对象
            for (JsonElement bean : jsonElements) {
                LocalCity bean1 = gson.fromJson(bean, LocalCity.class);//解析
                beans.add(bean1);
            }

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return beans;
    }

    public static ArrayList<LifeAddress> getLifeCity(Context context){
        InputStream inputStream = null;
        ArrayList<LifeAddress> beans = new ArrayList<>();
        try {
            inputStream = context.getAssets().open("city1.json");
            String str = convertStreamToString(inputStream);
            Gson gson = new Gson();

            LifeAddressResult lifeAddressResult = gson.fromJson(str, LifeAddressResult.class);
            beans = lifeAddressResult.city;

        }catch (IOException e){
            e.printStackTrace();
        }
        return beans;
    }

}
