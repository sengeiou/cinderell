package com.cinderellavip.bean.net.find;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jumpbox on 16/8/23.
 */
public class HotTopicItem implements Parcelable {
    public int id;

    public String title;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public HotTopicItem() {
    }

    protected HotTopicItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<HotTopicItem> CREATOR = new Parcelable.Creator<HotTopicItem>() {
        @Override
        public HotTopicItem createFromParcel(Parcel source) {
            return new HotTopicItem(source);
        }

        @Override
        public HotTopicItem[] newArray(int size) {
            return new HotTopicItem[size];
        }
    };
}
