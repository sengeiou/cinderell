package com.cinderellavip.util.banner;

import android.app.Activity;

import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;


public class LinkUtil {

    public static void setData(Activity context, int type,String value){
            //跳转类型（1无跳转，2跳转链接，3跳转商品详情，4跳转图文详情, 5跳转品牌详情, 6跳转店铺详情, 7跳转一级分类）
            switch (type) {
                case 2:
                    AgreementWebViewActivity.launch(context,value);
                    break;
                case 3:
                    GoodsDetailActivity.launch(context,value);
                    break;
                case 4:
                    WebViewActivity.launch(context,value,WebViewActivity.GRAPHIC);
                    break;
                case 5:
//                    BrandDetailActivity.launch(context);
                    break;
                case 6:
                    ShopDetailActivity.launchShop(context,value);
                    break;
                case 7:

                    break;
            }

    }





}
