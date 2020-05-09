package com.cinderellavip.util;


import com.cinderellavip.bean.net.home.HomeBanner;
import com.cinderellavip.bean.local.HomeGoods;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<String> getData(int size){
        List<String> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add("");
        }
        return data;
    }




    public static List<HomeGoods> getHomeGoods(int size){
        List<HomeGoods> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add(new HomeGoods(HomeGoods.FEATURED));
        }
        return data;
    }

    public static List<HomeGoods> getHomeGoods(int size,int type){
        List<HomeGoods> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add(new HomeGoods(type));
        }
        return data;
    }
    public static List<HomeBanner> getBannerData(int size){
        List<HomeBanner> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add(new HomeBanner());
        }
        return data;
    }

}
