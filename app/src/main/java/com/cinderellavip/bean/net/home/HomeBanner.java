package com.cinderellavip.bean.net.home;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;


public class HomeBanner extends SimpleBannerInfo {
    


    public String id;
    public String img;
    public int type;
    public String value;





    @Override
    public String getXBannerUrl() {
        return img;
    }


}
