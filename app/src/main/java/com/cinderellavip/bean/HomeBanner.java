package com.cinderellavip.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;


public class HomeBanner extends SimpleBannerInfo {
    


    public String logo;





    @Override
    public String getXBannerUrl() {
        return logo;
    }


}
