package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 立即结算和购物车结算参数
 */
public class RequestSettlePara implements Parcelable {
    public static int PRODUCT = 1;
    public static int CART = 2;
    public static int GROUP = 3;

    public int type;
    //购物车结算参数
    public String cart_ids;


    //立即结算参数  拼团购买 下面三个参数一样
    public String product_id;
    public String norm_id;
    public String number;


    //共有参数
    public String address_id = "0";
    public String coupon_ids = "0";

    public RequestSettlePara(int type, String cart_ids) {
        this.type = type;
        this.cart_ids = cart_ids;
    }

    public RequestSettlePara(int type, String product_id, String norm_id, String number) {
        this.type = type;
        this.product_id = product_id;
        this.norm_id = norm_id;
        this.number = number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.cart_ids);
        dest.writeString(this.product_id);
        dest.writeString(this.norm_id);
        dest.writeString(this.number);
        dest.writeString(this.address_id);
        dest.writeString(this.coupon_ids);
    }

    protected RequestSettlePara(Parcel in) {
        this.type = in.readInt();
        this.cart_ids = in.readString();
        this.product_id = in.readString();
        this.norm_id = in.readString();
        this.number = in.readString();
        this.address_id = in.readString();
        this.coupon_ids = in.readString();
    }

    public static final Parcelable.Creator<RequestSettlePara> CREATOR = new Parcelable.Creator<RequestSettlePara>() {
        @Override
        public RequestSettlePara createFromParcel(Parcel source) {
            return new RequestSettlePara(source);
        }

        @Override
        public RequestSettlePara[] newArray(int size) {
            return new RequestSettlePara[size];
        }
    };
}
