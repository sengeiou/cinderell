package com.cinderellavip.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BindLogin implements Parcelable {

    public String unionid;
    public String nickname;
    public String avatar;
    public String sex;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.unionid);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.sex);
    }

    public BindLogin() {
    }

    protected BindLogin(Parcel in) {
        this.unionid = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.sex = in.readString();
    }

    public static final Parcelable.Creator<BindLogin> CREATOR = new Parcelable.Creator<BindLogin>() {
        @Override
        public BindLogin createFromParcel(Parcel source) {
            return new BindLogin(source);
        }

        @Override
        public BindLogin[] newArray(int size) {
            return new BindLogin[size];
        }
    };

    @Override
    public String toString() {
        return "BindLogin{" +
                "unionid='" + unionid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
