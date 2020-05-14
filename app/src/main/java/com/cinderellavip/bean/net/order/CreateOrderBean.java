package com.cinderellavip.bean.net.order;

import android.os.Parcel;
import android.os.Parcelable;

public class CreateOrderBean implements Parcelable {


    public static int PRODUCT = 1;
    public static int CART = 2;


    public int type = 1;

    ////立即结算
    public String order_id;

    //公共的
    public String pay_amount;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.order_id);
        dest.writeString(this.pay_amount);
    }

    public CreateOrderBean() {
    }

    protected CreateOrderBean(Parcel in) {
        this.type = in.readInt();
        this.order_id = in.readString();
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
