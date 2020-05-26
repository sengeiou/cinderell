package com.cinderellavip.bean.net.order;

import com.cinderellavip.bean.local.SelectCouponsBean;

import java.util.List;

public class OrderSettleShopBean {


    public List<OrderSettleGoods> products;
    public SelectCouponsBean coupon;
    public boolean has_coupon;
    public OrderSettleShopAmount amount;

    public String store_name;
    public String store_id;

    public String remark = "";



}
