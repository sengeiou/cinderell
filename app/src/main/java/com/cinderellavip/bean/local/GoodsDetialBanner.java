package com.cinderellavip.bean.local;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;


public class GoodsDetialBanner extends SimpleBannerInfo {


    /**
     * 是视频的话  logo视频地址
     *
     * pic 缩率图
     *
     * 图片的话
     * logo代表地址
     */
    public String logo;
    public String pic;
    public boolean isVideo; //是否是视频

    public GoodsDetialBanner(String logo, boolean isVideo) {
        this.logo = logo;
        this.isVideo = isVideo;
    }


    public GoodsDetialBanner(String logo, String pic, boolean isVideo) {
        this.logo = logo;
        this.pic = pic;
        this.isVideo = isVideo;
    }

    @Override
    public String getXBannerUrl() {
        return logo;
    }


}
