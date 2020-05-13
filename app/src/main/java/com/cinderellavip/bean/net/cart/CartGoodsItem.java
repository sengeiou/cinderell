package com.cinderellavip.bean.net.cart;

import com.cinderellavip.util.ArithmeticUtil;

public class CartGoodsItem {


    public boolean isCheck;

    public int cart_id;
    public int product_id;
    public String product_thumb;
    public String product_name;
    public String product_norm;
    public double product_price;
    public int product_num;


    public String getProduct_price() {
        return ArithmeticUtil.convert(product_price);
    }
}
