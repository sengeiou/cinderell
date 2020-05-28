package com.cinderellavip.bean.direct;

public class DirectPerson {
    public int id;
    public int score;
    public double distance;
    public String avatar;
    public String name;
    public String work;
    public String longitude;
    public String latitude;
    public int reserve;

    public String getStatusText(){
        if (reserve == 0){
            return "今日不可约";

        }
        return "今日可约";
    }
}
