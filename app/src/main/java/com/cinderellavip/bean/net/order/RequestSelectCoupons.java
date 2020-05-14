package com.cinderellavip.bean.net.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.bean.local.SelectCouponsBean;

public class RequestSelectCoupons implements Parcelable {


    public String goods_amount;
    public String store_id;

    public SelectCouponsBean coupon;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goods_amount);
        dest.writeString(this.store_id);
        dest.writeParcelable(this.coupon, flags);
    }

    public RequestSelectCoupons() {
    }

    protected RequestSelectCoupons(Parcel in) {
        this.goods_amount = in.readString();
        this.store_id = in.readString();
        this.coupon = in.readParcelable(SelectCouponsBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<RequestSelectCoupons> CREATOR = new Parcelable.Creator<RequestSelectCoupons>() {
        @Override
        public RequestSelectCoupons createFromParcel(Parcel source) {
            return new RequestSelectCoupons(source);
        }

        @Override
        public RequestSelectCoupons[] newArray(int size) {
            return new RequestSelectCoupons[size];
        }
    };
}
