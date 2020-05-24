package com.cinderellavip.bean.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LifeCityBean implements Parcelable {


    public boolean isCheck;


	public int id;
	public String name;
	public String phone;
	public String title;
	public String address;
	public String doorplate;

	@SerializedName("default")
	public int is_default;
	//0 男 1 女
	public int gender;

	public boolean isDefault(){
		return is_default ==1;
	}

	public boolean isGender(){
		return is_default ==1;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.phone);
		dest.writeString(this.title);
		dest.writeString(this.address);
		dest.writeString(this.doorplate);
		dest.writeInt(this.is_default);
		dest.writeInt(this.gender);
	}

	public LifeCityBean() {
	}

	protected LifeCityBean(Parcel in) {
		this.isCheck = in.readByte() != 0;
		this.id = in.readInt();
		this.name = in.readString();
		this.phone = in.readString();
		this.title = in.readString();
		this.address = in.readString();
		this.doorplate = in.readString();
		this.is_default = in.readInt();
		this.gender = in.readInt();
	}

	public static final Parcelable.Creator<LifeCityBean> CREATOR = new Parcelable.Creator<LifeCityBean>() {
		@Override
		public LifeCityBean createFromParcel(Parcel source) {
			return new LifeCityBean(source);
		}

		@Override
		public LifeCityBean[] newArray(int size) {
			return new LifeCityBean[size];
		}
	};
}
