package com.cinderellavip.bean.net.life;


import com.cinderellavip.util.ArithmeticUtil;

/**
 */
public class ShortPreOrderResult {


    //生活订单数据
    private int id;

    private int suite;
    private String startTime;
    private String endTime;
    private int coupon;

    //共有的
    private double price;
    private double discount;
    private double actual;

    //直约下单数据
    private int project;
    private String servicetime;

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
