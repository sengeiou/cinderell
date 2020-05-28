package com.cinderellavip.bean.net.goods;

import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.bean.net.SpecialItem;

import java.util.List;

public class GoodsResult {
    public GoodsInfo product_info;
    public List<CouponsBean> coupons;
    public List<String> actives;
    public int comments_num;
    public List<HomeGoods> recommend_products;
    public List<SpecialItem> product_norm;
    public List<GoodsCommentItem> comments;
    public  GroupInfo group_info; //拼团商品
    public  SpikeInfo spike_info; //秒杀商品

    public String integral_rate;
    public  boolean user_is_vip;
}
