package com.cinderellavip.banner;

import android.content.Context;
import android.widget.ImageView;

import com.cinderellavip.R;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.BigImageActivity;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class BannerUtil {
    public static void initBanner(Context context, XBanner xbanner, String pics) {
        String[] split = pics.split(";");
        if (split.length<=1){
            xbanner.setPointsIsVisible(false);
        }else {
            xbanner.setPointsIsVisible(true);
        }
        List<GoodsDetailBanner> data = new ArrayList<>();
        for (String s : split) {
            data.add(new GoodsDetailBanner(s));
        }
        xbanner.setBannerData(R.layout.item_viewpager_goods_detail, data);
        xbanner.setAutoPlayAble(true);
        xbanner.loadImage(((banner, model, view, position) -> {
            ImageView image = view.findViewById(R.id.image);
            ImageUtil.loadNet(context, image, ((GoodsDetailBanner) model).getXBannerUrl());
        }
        ));
        xbanner.setOnItemClickListener(((banner, model, view, position) -> {
            List<String> list = new ArrayList<>();
            for (GoodsDetailBanner bean:data){
                list.add(bean.logo);
            }
            String[] array =new String[list.size()];
            list.toArray(array);
            BigImageActivity.launch(context,array,position);
        }));
    }
}
