package com.cinderellavip.bean.net.life;


import android.os.Parcel;
import android.os.Parcelable;

/**
 */
public class ShortTime implements Parcelable {

    public  boolean isCheck;


    public String start;
    public String end;
    public String day;
    //0:可选 1：不可选择
    public int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeString(this.day);
        dest.writeInt(this.id);
    }

    public ShortTime() {
    }

    protected ShortTime(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.start = in.readString();
        this.end = in.readString();
        this.day = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<ShortTime> CREATOR = new Parcelable.Creator<ShortTime>() {
        @Override
        public ShortTime createFromParcel(Parcel source) {
            return new ShortTime(source);
        }

        @Override
        public ShortTime[] newArray(int size) {
            return new ShortTime[size];
        }
    };
}
