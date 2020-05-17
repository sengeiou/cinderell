package com.cinderellavip.bean.local;

public class GoodsDetialBanner {


    /**
     * 是视频的话  logo视频地址
     *
     * pic 缩率图
     *
     * 图片的话
     * logo代表地址
     */
    public String pic;
    public boolean isVideo; //是否是视频
    public String video; //是否是视频


    public GoodsDetialBanner( String pic, boolean isVideo) {
        this.pic = pic;
        this.isVideo = isVideo;
    }

    public GoodsDetialBanner(String pic, boolean isVideo, String video) {
        this.pic = pic;
        this.isVideo = isVideo;
        this.video = video;
    }
}
