package com.cinderellavip.bean.net.goods;

import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class GoodsInfo {
    public int id;
    public int store_id;
    public int brand_id;
    public int sale;

    public List<String> images;

    public String video;
    public String name;
    public String send_area;
    public String brand_image;
    public String brand_name;
    public String detail;
    public String store_name;

    public  boolean freight;
    public  boolean hasGroup;

    public double price;
    public double old_price;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }



    public String getOld_price() {
        return ArithmeticUtil.convert(old_price);
    }




    public String getShip() {
        return freight?"包邮":"不包邮";
    }



}
