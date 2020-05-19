package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;

public class HomeGoods implements Parcelable {






    public int id;
    public String name;
    public String thumb;

    public double price;
    public double old_price;
    public int number;
    public double buy_price;
    public double group_price;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }

    public String getOld_price() {
        return ArithmeticUtil.convert(old_price);
    }

    public String getBuy_price() {
        return ArithmeticUtil.convert(buy_price);
    }

    public String getGroup_price() {
        return ArithmeticUtil.convert(group_price);
    }


    //搜索的时候 会有下面数据
    public String store_id;
    public String store_name;

    //帖子详情的时候
    public int product_id;
    public String product_name;
    public String product_thumb;
    public double product_price;
    public String category;

    public String getProduct_price() {
        return ArithmeticUtil.convert(product_price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.thumb);
        dest.writeDouble(this.price);
        dest.writeDouble(this.old_price);
        dest.writeInt(this.number);
        dest.writeDouble(this.buy_price);
        dest.writeDouble(this.group_price);
        dest.writeString(this.store_id);
        dest.writeString(this.store_name);
        dest.writeInt(this.product_id);
        dest.writeString(this.product_name);
        dest.writeString(this.product_thumb);
        dest.writeDouble(this.product_price);
        dest.writeString(this.category);
    }

    public HomeGoods() {
    }

    protected HomeGoods(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.thumb = in.readString();
        this.price = in.readDouble();
        this.old_price = in.readDouble();
        this.number = in.readInt();
        this.buy_price = in.readDouble();
        this.group_price = in.readDouble();
        this.store_id = in.readString();
        this.store_name = in.readString();
        this.product_id = in.readInt();
        this.product_name = in.readString();
        this.product_thumb = in.readString();
        this.product_price = in.readDouble();
        this.category = in.readString();
    }

    public static final Parcelable.Creator<HomeGoods> CREATOR = new Parcelable.Creator<HomeGoods>() {
        @Override
        public HomeGoods createFromParcel(Parcel source) {
            return new HomeGoods(source);
        }

        @Override
        public HomeGoods[] newArray(int size) {
            return new HomeGoods[size];
        }
    };
}
