package com.cinderellavip.bean.net.order;

import com.cinderellavip.bean.local.CouponsBean;
import com.cinderellavip.bean.net.NetCityBean;

import java.util.List;

public class OrderSettleShopBean {


    public List<OrderSettleGoods> products;
    public CouponsBean coupon;
    public boolean has_coupon;
    public OrderSettleShopAmount amount;

    public String store_name;
    public String store_id;


}
