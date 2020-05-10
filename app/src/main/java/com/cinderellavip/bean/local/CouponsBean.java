package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;

public class CouponsBean implements Parcelable {


    public boolean isCheck;

    public int id;
    public String title;
    public String condition;
    public String type;
    public double amount;

    //是否可领取
    public boolean status;

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
        dest.writeString(this.condition);
        dest.writeString(this.type);
        dest.writeDouble(this.amount);
        dest.writeByte(this.status ? (byte) 1 : (byte) 0);
    }

    public CouponsBean() {
    }

    protected CouponsBean(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.id = in.readInt();
        this.title = in.readString();
        this.condition = in.readString();
        this.type = in.readString();
        this.amount = in.readDouble();
        this.status = in.readByte() != 0;
    }

    public static final Parcelable.Creator<CouponsBean> CREATOR = new Parcelable.Creator<CouponsBean>() {
        @Override
        public CouponsBean createFromParcel(Parcel source) {
            return new CouponsBean(source);
        }

        @Override
        public CouponsBean[] newArray(int size) {
            return new CouponsBean[size];
        }
    };
}
