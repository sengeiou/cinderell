package com.cinderellavip.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PrePayLongOrder implements Parcelable {
    public String contracts_id;
    public String coupon;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.contracts_id);
        dest.writeString(this.coupon);
    }

    public PrePayLongOrder() {
    }

    protected PrePayLongOrder(Parcel in) {
        this.contracts_id = in.readString();
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
}
