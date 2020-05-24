package com.cinderellavip.bean.net.life;


import android.os.Parcel;
import android.os.Parcelable;

public class PreLongOrderResult implements Parcelable {


    public String service_name;
    public String service_project_name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.service_name);
        dest.writeString(this.service_project_name);
    }

    public PreLongOrderResult() {
    }

    protected PreLongOrderResult(Parcel in) {
        this.service_name = in.readString();
        this.service_project_name = in.readString();
    }

    public static final Parcelable.Creator<PreLongOrderResult> CREATOR = new Parcelable.Creator<PreLongOrderResult>() {
        @Override
        public PreLongOrderResult createFromParcel(Parcel source) {
            return new PreLongOrderResult(source);
        }

        @Override
        public PreLongOrderResult[] newArray(int size) {
            return new PreLongOrderResult[size];
        }
    };
}
