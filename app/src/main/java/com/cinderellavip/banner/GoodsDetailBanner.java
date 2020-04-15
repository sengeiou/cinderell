package com.cinderellavip.banner;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;


public class GoodsDetailBanner extends SimpleBannerInfo {
    
    





    public String logo;

    public GoodsDetailBanner(String logo) {
        this.logo = logo;
    }

    @Override
    public String getXBannerUrl() {
        return logo;
    }


}
