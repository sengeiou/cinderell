package com.cinderellavip.bean.net.order;

import android.os.Parcel;
import android.os.Parcelable;

public class CreateOrderBean implements Parcelable {




    public int order_id;
    public String pay_amount;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.order_id);
        dest.writeString(this.pay_amount);
    }

    public CreateOrderBean() {
    }

    protected CreateOrderBean(Parcel in) {
        this.order_id = in.readInt();
        this.pay_amount = in.readString();
    }

    public static final Parcelable.Creator<CreateOrderBean> CREATOR = new Parcelable.Creator<CreateOrderBean>() {
        @Override
        public CreateOrderBean createFromParcel(Parcel source) {
            return new CreateOrderBean(source);
        }

        @Override
        public CreateOrderBean[] newArray(int size) {
            return new CreateOrderBean[size];
        }
    };
}
