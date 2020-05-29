package com.cinderellavip.bean.direct;

public class DirectMapPersonItem {
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

    @Override
    public String toString() {
        return "DirectPerson{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
