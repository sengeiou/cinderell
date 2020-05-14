package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;

public class SelectCouponsBean implements Parcelable {


    public boolean isCheck;

    public int id;
    public String title;
    public double amount;
    public String condition;
    public String expire_date;
    public String type;

    public String getAmount() {
        return ArithmeticUtil.convert(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeDouble(this.amount);
        dest.writeString(this.condition);
        dest.writeString(this.expire_date);
        dest.writeString(this.type);
    }

    public SelectCouponsBean() {
    }

    protected SelectCouponsBean(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.id = in.readInt();
        this.title = in.readString();
        this.amount = in.readDouble();
        this.condition = in.readString();
        this.expire_date = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<SelectCouponsBean> CREATOR = new Parcelable.Creator<SelectCouponsBean>() {
        @Override
        public SelectCouponsBean createFromParcel(Parcel source) {
            return new SelectCouponsBean(source);
        }

        @Override
        public SelectCouponsBean[] newArray(int size) {
            return new SelectCouponsBean[size];
        }
    };
}
