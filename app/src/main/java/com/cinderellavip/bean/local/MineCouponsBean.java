package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;

public class MineCouponsBean implements Parcelable {



    public boolean isCheck;

    public int id;
    public int store_id;
    public String title;
    public String condition;
    public String expire_date;
    public String type;
    public double amount;

    //是否可领取
    public int status;

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
        dest.writeInt(this.store_id);
        dest.writeString(this.title);
        dest.writeString(this.condition);
        dest.writeString(this.type);
        dest.writeDouble(this.amount);
        dest.writeInt(this.status);
    }

    public MineCouponsBean() {
    }

    protected MineCouponsBean(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.id = in.readInt();
        this.store_id = in.readInt();
        this.title = in.readString();
        this.condition = in.readString();
        this.type = in.readString();
        this.amount = in.readDouble();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<MineCouponsBean> CREATOR = new Parcelable.Creator<MineCouponsBean>() {
        @Override
        public MineCouponsBean createFromParcel(Parcel source) {
            return new MineCouponsBean(source);
        }

        @Override
        public MineCouponsBean[] newArray(int size) {
            return new MineCouponsBean[size];
        }
    };
}
