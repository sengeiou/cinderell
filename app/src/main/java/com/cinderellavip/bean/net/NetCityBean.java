package com.cinderellavip.bean.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NetCityBean implements Parcelable {


    public boolean isCheck;

	public NetCityBean(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public int id;
	public String name;
	public String mobile;
	public String province;
	public String city;
	public String area;
	public String address;


	@SerializedName("default")
	public boolean is_default;


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.mobile);
		dest.writeString(this.province);
		dest.writeString(this.city);
		dest.writeString(this.area);
		dest.writeString(this.address);
		dest.writeByte(this.is_default ? (byte) 1 : (byte) 0);
	}

	protected NetCityBean(Parcel in) {
		this.isCheck = in.readByte() != 0;
		this.id = in.readInt();
		this.name = in.readString();
		this.mobile = in.readString();
		this.province = in.readString();
		this.city = in.readString();
		this.area = in.readString();
		this.address = in.readString();
		this.is_default = in.readByte() != 0;
	}

	public static final Parcelable.Creator<NetCityBean> CREATOR = new Parcelable.Creator<NetCityBean>() {
		@Override
		public NetCityBean createFromParcel(Parcel source) {
			return new NetCityBean(source);
		}

		@Override
		public NetCityBean[] newArray(int size) {
			return new NetCityBean[size];
		}
	};
}
