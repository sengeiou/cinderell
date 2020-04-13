package com.cinderellavip.util;


import com.cinderellavip.bean.HomeBanner;

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

    public static List<HomeBanner> getBannerData(int size){
        List<HomeBanner> data = new ArrayList<>();
        for (int i=0;i<size;i++){
            data.add(new HomeBanner());
        }
        return data;
    }

}
