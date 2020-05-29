package com.cinderellavip.bean.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 为了预下单
 */
public class LifePreOrder implements Parcelable {


    //短期项目
    public static final int SHORT = 1;
    //直约项目
    public static final int DIRECT = 2;

    public int type;

    //共有
    public int project;
    public String city;

    //直约项目 下单时间
    public String servicetime;
    //员工id 为了获取时间
    public String waiter;


    public LifePreOrder(int type, int project, String city, String servicetime) {
        this.type = type;
        this.project = project;
        this.city = city;
        this.servicetime = servicetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeInt(this.project);
        dest.writeString(this.city);
        dest.writeString(this.servicetime);
        dest.writeString(this.waiter);
    }

    public LifePreOrder() {
    }

    protected LifePreOrder(Parcel in) {
        this.type = in.readInt();
        this.project = in.readInt();
        this.city = in.readString();
        this.servicetime = in.readString();
        this.waiter = in.readString();
    }

    public static final Parcelable.Creator<LifePreOrder> CREATOR = new Parcelable.Creator<LifePreOrder>() {
        @Override
        public LifePreOrder createFromParcel(Parcel source) {
            return new LifePreOrder(source);
        }

        @Override
        public LifePreOrder[] newArray(int size) {
            return new LifePreOrder[size];
        }
    };
}
