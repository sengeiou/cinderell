package com.cinderellavip.bean.net.life;


import android.os.Parcel;
import android.os.Parcelable;

/**
 */
public class ShortDate implements Parcelable {




    //用来表示今天 还是明天
    public String tip;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tip);
        dest.writeString(this.nian);
        dest.writeString(this.yue);
        dest.writeString(this.ri);
        dest.writeString(this.week);
    }

    public ShortDate() {
    }

    protected ShortDate(Parcel in) {
        this.tip = in.readString();
        this.nian = in.readString();
        this.yue = in.readString();
        this.ri = in.readString();
        this.week = in.readString();
    }

    public static final Parcelable.Creator<ShortDate> CREATOR = new Parcelable.Creator<ShortDate>() {
        @Override
        public ShortDate createFromParcel(Parcel source) {
            return new ShortDate(source);
        }

        @Override
        public ShortDate[] newArray(int size) {
            return new ShortDate[size];
        }
    };
}
