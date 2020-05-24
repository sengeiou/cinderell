package com.cinderellavip.bean.net.life;


import com.cinderellavip.util.ArithmeticUtil;

/**
 */
public class ShortPreOrderResult {


    private int id;
    private double price;
    private int suite;
    private String startTime;
    private String endTime;
    private int coupon;
    private double discount;
    private double actual;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public String getDiscount() {
        return ArithmeticUtil.convert(discount);
    }

    public String getActual() {
        return ArithmeticUtil.convert(actual);
    }
}
