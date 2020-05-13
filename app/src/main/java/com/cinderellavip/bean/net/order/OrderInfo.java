package com.cinderellavip.bean.net.order;

import com.cinderellavip.bean.net.NetCityBean;

import java.util.List;

public class OrderInfo {




    public List<OrderGoodsInfo> goods;
    public NetCityBean address;


  

    public int id;
    public String order_no;
    public String goods_amount;
    public String ship_amount;
    public String dis_amount;
    public String total_amount;
    public String payment;
    public int status;
    public String status_txt;
    public String pay_sn;
    public String pay_at;
    public String create_at;
    public String send_at;
    public String send_sn;
    public String send_company;
    public String receipt_at;
    public String commit_at;
    public int store_id;
    public String store_name;

}
