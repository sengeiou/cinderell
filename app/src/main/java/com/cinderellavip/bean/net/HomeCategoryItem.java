package com.cinderellavip.bean.net;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeCategoryItem implements Parcelable {

    public boolean isCheck;


    public int id;
    public String name;
    public String image;
    public String type;

    // type == -1  代表更多
    public HomeCategoryItem(String type) {
        this.type = type;
    }

    //id == 0  代表首页
    public HomeCategoryItem(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.type);
    }

    protected HomeCategoryItem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<HomeCategoryItem> CREATOR = new Parcelable.Creator<HomeCategoryItem>() {
        @Override
        public HomeCategoryItem createFromParcel(Parcel source) {
            return new HomeCategoryItem(source);
        }

        @Override
        public HomeCategoryItem[] newArray(int size) {
            return new HomeCategoryItem[size];
        }
    };
}
