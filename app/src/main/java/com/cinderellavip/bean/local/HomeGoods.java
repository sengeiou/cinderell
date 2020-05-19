package com.cinderellavip.bean.local;

import com.cinderellavip.util.ArithmeticUtil;

public class HomeGoods {






    public int id;
    public String name;
    public String thumb;

    public double price;
    public double old_price;
    public int number;
    public double buy_price;
    public double group_price;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public String getOld_price() {
        return ArithmeticUtil.convert(old_price);
    }

    public String getBuy_price() {
        return ArithmeticUtil.convert(buy_price);
    }

    public String getGroup_price() {
        return ArithmeticUtil.convert(group_price);
    }


    //搜索的时候 会有下面数据
    public String store_id;
    public String store_name;

    //帖子详情的时候
    public int product_id;
    public String product_name;
    public String product_thumb;
    public double product_price;
    public String category;

    public String getProduct_price() {
        return ArithmeticUtil.convert(product_price);
    }
}
