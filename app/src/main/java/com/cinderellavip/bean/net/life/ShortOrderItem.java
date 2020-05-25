package com.cinderellavip.bean.net.life;

import com.cinderellavip.bean.net.LifeCityBean;
import com.cinderellavip.util.ArithmeticUtil;

public class ShortOrderItem {
    public String code;
    public  int id;
    //1:待确认 2：待支付 3：已支付 4:服务中 5：待评价 6：完成 7:已取消
    public  int status;
    public  int pay;

    public  String icon;
    public  String sortname;
    public  String starttime;
    public  String title;
    public  double price;

    public double discount;
    public double actual;

    public String getDiscount() {
        return ArithmeticUtil.convert(discount);
    }

    public String getActual() {
        return ArithmeticUtil.convert(actual);
    }

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public LifeCityBean address;


    public String getStatus() {
        String s = "";
        if (pay == 0){
            s = "待支付";
        }else if (pay == 1){
            switch (status){
                case -1:
                    s = "待分配";
                    break;
                case 0:
                    s = "待服务";
                    break;
                case 1:
                    s = "服务中";
                    break;
                case 2:
                    s = "待评价";
                    break;
                case 3:
                    s = "已取消";
                    break;
                case 4:
                    s = "已完成";
                    break;
            }
        }else if (pay == 2){
            s = "已取消";
        }
        return s;
    }
}
