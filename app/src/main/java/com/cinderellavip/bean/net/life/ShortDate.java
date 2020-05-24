package com.cinderellavip.bean.net.life;


/**
 */
public class ShortDate {



    public String nian;
    public String yue;
    public String ri;
    public String week;

    public String getWeek() {
        String s = "";
        int i= Integer.parseInt(week);
        switch (i){
            case 1:
                s = "周一";
                break;
            case 2:
                s = "周二";
                break;
            case 3:
                s = "周三";
                break;
            case 4:
                s = "周四";
                break;
            case 5:
                s = "周五";
                break;
            case 6:
                s = "周六";
                break;
            case 7:
                s = "周日";
                break;
        }
        return s;
    }
}
