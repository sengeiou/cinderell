package com.cinderellavip.bean.net.life;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;

public class LifeCoupon implements Parcelable {
    public int id;
    public int scenes;
    public int receive;
    public String title;
    public double full;
    public double less;
    public String start;
    public String end;


    public String getFull() {
        return ArithmeticUtil.convert(full);
    }

    public String getLess() {
        return ArithmeticUtil.convert(less);
    }

    public String getScenes() {
        if (scenes == 1){
            return "全场通用";
        }else {
            return "部分分类可用";
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.scenes);
        dest.writeInt(this.receive);
        dest.writeString(this.title);
        dest.writeDouble(this.full);
        dest.writeDouble(this.less);
        dest.writeString(this.start);
        dest.writeString(this.end);
    }

    public LifeCoupon() {
    }

    protected LifeCoupon(Parcel in) {
        this.id = in.readInt();
        this.scenes = in.readInt();
        this.receive = in.readInt();
        this.title = in.readString();
        this.full = in.readDouble();
        this.less = in.readDouble();
        this.start = in.readString();
        this.end = in.readString();
    }

    public static final Parcelable.Creator<LifeCoupon> CREATOR = new Parcelable.Creator<LifeCoupon>() {
        @Override
        public LifeCoupon createFromParcel(Parcel source) {
            return new LifeCoupon(source);
        }

        @Override
        public LifeCoupon[] newArray(int size) {
            return new LifeCoupon[size];
        }
    };
}
