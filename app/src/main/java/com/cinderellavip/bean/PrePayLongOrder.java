package com.cinderellavip.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class PrePayLongOrder implements Parcelable {
    //长期订单
    public static final int LONG = 0;
    //服务项目
    public static final int PROJECT = 1;


    public int type;

    //长期服务
    public String contracts_id;


    //服务项目 为了下单用 下单成功 项目id（project）变成 订单id。为了结算用
    public String project;
    public String address;
    public String starttime;
    public String endtime;
    public String city;

    //共有的
    public String coupon;


    //购买服务项目的时候
    public PrePayLongOrder(String project, String city) {
        this.project = project;
        this.city = city;
        this.address = "";
        this.starttime = "";
        this.endtime = "";
        this.coupon = "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.contracts_id);
        dest.writeString(this.project);
        dest.writeString(this.address);
        dest.writeString(this.starttime);
        dest.writeString(this.endtime);
        dest.writeString(this.city);
        dest.writeString(this.coupon);
    }

    public PrePayLongOrder() {
    }

    protected PrePayLongOrder(Parcel in) {
        this.type = in.readInt();
        this.contracts_id = in.readString();
        this.project = in.readString();
        this.address = in.readString();
        this.starttime = in.readString();
        this.endtime = in.readString();
        this.city = in.readString();
        this.coupon = in.readString();
    }

    public static final Parcelable.Creator<PrePayLongOrder> CREATOR = new Parcelable.Creator<PrePayLongOrder>() {
        @Override
        public PrePayLongOrder createFromParcel(Parcel source) {
            return new PrePayLongOrder(source);
        }

        @Override
        public PrePayLongOrder[] newArray(int size) {
            return new PrePayLongOrder[size];
        }
    };

    @Override
    public String toString() {
        return "PrePayLongOrder{" +
                "type=" + type +
                ", contracts_id='" + contracts_id + '\'' +
                ", project='" + project + '\'' +
                ", address='" + address + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", city='" + city + '\'' +
                ", coupon='" + coupon + '\'' +
                '}';
    }
}
