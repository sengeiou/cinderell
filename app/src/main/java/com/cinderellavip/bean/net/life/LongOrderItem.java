package com.cinderellavip.bean.net.life;

import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.util.ArithmeticUtil;

public class LongOrderItem {
    public String code;
    public  int id;
    //1:待确认 2：待支付 3：已支付 4:服务中 5：待评价 6：完成 7:已取消
    public  int type;
    public  String service_name;
    public  String service_icon;
    public  String project_title;
    public  String project_service;
    public  int cycle;
    public  int cont_id;
    public  double price;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public LifeCityBean address;


    public String getStatus() {
        String status = "";
        switch (type){
            case 1:
                status = "待确认";
                break;
            case 2:
                status = "待支付";
                break;
            case 3:
                status = "待服务";
                break;
            case 4:
                status = "服务中";
                break;
            case 5:
                status = "待评价";
                break;
            case 6:
                status = "已完成";
                break;
            case 7:
                status = "已取消";
                break;
        }
        return status;
    }
}
