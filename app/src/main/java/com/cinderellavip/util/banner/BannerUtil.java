package com.cinderellavip.util.banner;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.bean.net.home.HomeBanner;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.WebViewActivity;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;
import com.cinderellavip.ui.web.AgreementWebViewActivity;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;


public class BannerUtil {
    /*
    banner_type  类型 0：无连接 1：超链接 2：图文 3：商品分类 4：菜谱分类 5：活动 6：商品 7：菜谱
    banner_type_id 商品分类id/菜谱分类id/活动id/商品id/菜谱id
     */
    public static void setData(Activity context, XBanner xbanner, List<HomeBanner> data){
        if (data == null || data.size() == 0){
            xbanner.setVisibility(View.GONE);
        }
        xbanner.setBannerData(R.layout.item_home_banner, data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageView image = view.findViewById(R.id.image);
            ImageUtil.loadNet(context, image, ((HomeBanner) model).getXBannerUrl());
        }
        ));
        xbanner.setOnItemClickListener(((banner, model, view, position) -> {
            HomeBanner model1 = (HomeBanner) model;
            LinkUtil.setData(context,model1.type,((HomeBanner) model).value);
        }));
    }

    public static void setData1(Activity context, XBanner xbanner, List<HomeBanner> data){

        xbanner.setBannerData(data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageUtil.loadNet(context, (ImageView) view, ((HomeBanner) model).getXBannerUrl());
        }
        ));
        xbanner.setOnItemClickListener(((banner, model, view, position) -> {
            HomeBanner model1 = (HomeBanner) model;
            //跳转类型（1无跳转，2跳转链接，3跳转商品详情，4跳转图文详情, 5跳转品牌详情, 6跳转店铺详情, 7跳转一级分类）
            switch (model1.type) {
                case 2:
                    AgreementWebViewActivity.launch(context,((HomeBanner) model).value);
                    break;
                case 3:
                    GoodsDetailActivity.launch(context,((HomeBanner) model).value);
                    break;
                case 4:
                    WebViewActivity.launch(context,((HomeBanner) model).value,WebViewActivity.GRAPHIC);
                    break;
                case 5:
//                    BrandDetailActivity.launch(context);
                    break;
                case 6:
                    ShopDetailActivity.launchShop(context,((HomeBanner) model).value);
                    break;
                case 7:

                    break;
            }

        }));
    }




}
