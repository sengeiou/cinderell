package com.cinderellavip.util;


import com.cinderellavip.R;
import com.cinderellavip.bean.HomeBanner;
import com.cinderellavip.bean.local.HomeCategoryItem;
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

    public static List<HomeCategoryItem> getShopHomeCategory(){
        List<HomeCategoryItem> data = new ArrayList<>();
        data.add(new HomeCategoryItem("超值优惠", R.mipmap.home_category1));
        data.add(new HomeCategoryItem("美妆", R.mipmap.meizhuang));
        data.add(new HomeCategoryItem("今日爆款", R.mipmap.jrbk));
        data.add(new HomeCategoryItem("个人洗护", R.mipmap.grxh));
        data.add(new HomeCategoryItem("全球购", R.mipmap.qqg));
        data.add(new HomeCategoryItem("母婴", R.mipmap.muying));
        data.add(new HomeCategoryItem("超级工厂", R.mipmap.cjgc));
        data.add(new HomeCategoryItem("宠物", R.mipmap.chongwu));
        data.add(new HomeCategoryItem("大牌特卖", R.mipmap.dptm));
        data.add(new HomeCategoryItem("家具", R.mipmap.jiaju));
        data.add(new HomeCategoryItem("食品", R.mipmap.shipin));
        data.add(new HomeCategoryItem("数码电子", R.mipmap.smdz));
        data.add(new HomeCategoryItem("服饰鞋包", R.mipmap.fsxb));
        data.add(new HomeCategoryItem("童装童鞋", R.mipmap.tztx));
        data.add(new HomeCategoryItem("办公用品", R.mipmap.bgjd));
        data.add(new HomeCategoryItem("礼品鲜花", R.mipmap.lpxh));
        data.add(new HomeCategoryItem("生鲜特产", R.mipmap.sxsc));
        data.add(new HomeCategoryItem("医疗保健", R.mipmap.ylbj));
        data.add(new HomeCategoryItem("厨具", R.mipmap.chuju));
        data.add(new HomeCategoryItem("图书文娱", R.mipmap.tswy));
        return data;
    }

    public static List<HomeCategoryItem> getLifeCategory(){
        List<HomeCategoryItem> data = new ArrayList<>();
        data.add(new HomeCategoryItem("日常保洁", R.mipmap.life_banner1));
        data.add(new HomeCategoryItem("家电维修", R.mipmap.life_banner2));
        data.add(new HomeCategoryItem("清洗养护", R.mipmap.life_banner3));
        data.add(new HomeCategoryItem("房屋维修", R.mipmap.life_banner4));
        data.add(new HomeCategoryItem("保姆", R.mipmap.life_banner5));
        data.add(new HomeCategoryItem("家教", R.mipmap.life_banner6));
        data.add(new HomeCategoryItem("月嫂", R.mipmap.life_banner7));
        data.add(new HomeCategoryItem("服务清单", R.mipmap.life_banner8));
        data.add(new HomeCategoryItem("育儿嫂", R.mipmap.life_banner9));
        data.add(new HomeCategoryItem("直约", R.mipmap.life_banner10));
        data.add(new HomeCategoryItem("日常保洁", R.mipmap.life_banner1));
        data.add(new HomeCategoryItem("家电维修", R.mipmap.life_banner2));
        data.add(new HomeCategoryItem("清洗养护", R.mipmap.life_banner3));
        data.add(new HomeCategoryItem("房屋维修", R.mipmap.life_banner4));
        data.add(new HomeCategoryItem("保姆", R.mipmap.life_banner5));
        data.add(new HomeCategoryItem("家教", R.mipmap.life_banner6));
        data.add(new HomeCategoryItem("月嫂", R.mipmap.life_banner7));
        data.add(new HomeCategoryItem("服务清单", R.mipmap.life_banner8));
        data.add(new HomeCategoryItem("育儿嫂", R.mipmap.life_banner9));
        data.add(new HomeCategoryItem("直约", R.mipmap.life_banner10));

        return data;
    }

    public static List<HomeCategoryItem> getShopOtherCategory(){
        List<HomeCategoryItem> data = new ArrayList<>();
        data.add(new HomeCategoryItem("食品", R.mipmap.shipin));
        data.add(new HomeCategoryItem("数码电子", R.mipmap.smdz));
        data.add(new HomeCategoryItem("服饰鞋包", R.mipmap.fsxb));
        data.add(new HomeCategoryItem("童装童鞋", R.mipmap.tztx));
        data.add(new HomeCategoryItem("办公用品", R.mipmap.bgjd));
        data.add(new HomeCategoryItem("礼品鲜花", R.mipmap.lpxh));
        data.add(new HomeCategoryItem("生鲜特产", R.mipmap.sxsc));
        data.add(new HomeCategoryItem("医疗保健", R.mipmap.ylbj));
        data.add(new HomeCategoryItem("厨具", R.mipmap.chuju));
        data.add(new HomeCategoryItem("更多", R.mipmap.tswy));
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
