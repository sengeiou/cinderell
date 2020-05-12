package com.cinderellavip.bean.net;


import com.cinderellavip.util.ArithmeticUtil;

public class SpecialItem {

    public boolean isCheck;

    public int id;
    public String name;
    public String thumb;
    public double price;
    public double old_price;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public String getOld_price() {
        return ArithmeticUtil.convert(old_price);
    }


}
