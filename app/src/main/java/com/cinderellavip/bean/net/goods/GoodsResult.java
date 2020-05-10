package com.cinderellavip.bean.net.goods;

import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.local.HomeGoods;

import java.util.List;

public class GoodsResult {
    public GoodsInfo product_info;
    public List<CouponsBean> coupons;
    public List<String> actives;
    public int comments_num;
    public List<HomeGoods> recommend_products;
    public List<GoodsCommentItem> comments;
    public  GroupInfo group_info;
}
