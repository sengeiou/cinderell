package com.cinderellavip.bean.net.life;


import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.util.ArithmeticUtil;

public class LongOrderDetailResult {



    public int id;
    public String code;
    public int project;
    public String project_title;
    public String project_service;
    public String service_icon;
    public String service_name;
    public int cycle;
    public String starttime;
    public String endtime;
    public LifeCityBean address;
    public String claim;
    public String cont_code;
    public int cont_id;
    public String cont_startTime;
    public String cont_endTime;
    public double price;
    public double discount;
    public double actual;
    public int type;
    public String url;

    //生活服务

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
