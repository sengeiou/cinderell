package com.cinderellavip.bean.net.life;

import android.os.Parcel;
import android.os.Parcelable;

public class LiftHomeCategory implements Parcelable {
    public int id;
    public int type;
    public String name;
    public String icon;
    public int one;
    public int tow;
    public int three;
    public int sort;
    public String create_at;
    public String update_at;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeInt(this.one);
        dest.writeInt(this.tow);
        dest.writeInt(this.three);
        dest.writeInt(this.sort);
        dest.writeString(this.create_at);
        dest.writeString(this.update_at);
    }

    public LiftHomeCategory() {
    }

    protected LiftHomeCategory(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.name = in.readString();
        this.icon = in.readString();
        this.one = in.readInt();
        this.tow = in.readInt();
        this.three = in.readInt();
        this.sort = in.readInt();
        this.create_at = in.readString();
        this.update_at = in.readString();
    }

    public static final Parcelable.Creator<LiftHomeCategory> CREATOR = new Parcelable.Creator<LiftHomeCategory>() {
        @Override
        public LiftHomeCategory createFromParcel(Parcel source) {
            return new LiftHomeCategory(source);
        }

        @Override
        public LiftHomeCategory[] newArray(int size) {
            return new LiftHomeCategory[size];
        }
    };
}
