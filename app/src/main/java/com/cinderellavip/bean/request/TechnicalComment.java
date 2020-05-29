package com.cinderellavip.bean.request;

import android.os.Parcel;
import android.os.Parcelable;

public class TechnicalComment implements Parcelable {


    //评价的时候 代表 技师  项目详情的时候 代表 项目
    public static final int comment_technical = 1;
    //评价的时候 代表 项目  项目详情的时候 代表 套餐
    public static final int comment_project = 2;

    public String waiter;
    public String project;
    public int type;


    public TechnicalComment(String waiter, String project, int type) {
        this.waiter = waiter;
        this.project = project;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.waiter);
        dest.writeString(this.project);
        dest.writeInt(this.type);
    }

    protected TechnicalComment(Parcel in) {
        this.waiter = in.readString();
        this.project = in.readString();
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<TechnicalComment> CREATOR = new Parcelable.Creator<TechnicalComment>() {
        @Override
        public TechnicalComment createFromParcel(Parcel source) {
            return new TechnicalComment(source);
        }

        @Override
        public TechnicalComment[] newArray(int size) {
            return new TechnicalComment[size];
        }
    };
}
