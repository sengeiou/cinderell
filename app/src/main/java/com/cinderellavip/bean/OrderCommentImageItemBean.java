package com.cinderellavip.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderCommentImageItemBean implements Parcelable {

    //文件的路径
    public String path;
    public String netPath;

    public OrderCommentImageItemBean(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.netPath);
    }

    public OrderCommentImageItemBean() {
    }

    protected OrderCommentImageItemBean(Parcel in) {
        this.path = in.readString();
        this.netPath = in.readString();
    }

    public static final Parcelable.Creator<OrderCommentImageItemBean> CREATOR = new Parcelable.Creator<OrderCommentImageItemBean>() {
        @Override
        public OrderCommentImageItemBean createFromParcel(Parcel source) {
            return new OrderCommentImageItemBean(source);
        }

        @Override
        public OrderCommentImageItemBean[] newArray(int size) {
            return new OrderCommentImageItemBean[size];
        }
    };
}
