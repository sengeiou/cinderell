package com.cinderellavip.bean.local;

import android.os.Parcel;
import android.os.Parcelable;

import com.cinderellavip.util.ArithmeticUtil;


public class CouponsBean implements Parcelable {
    public static final int NORMAL = 0,OBTAINED = 1,RECEIVED = 2,NO_HAVE = 3;

    public CouponsBean(int status) {
        this.status = status;
    }

    public boolean isCheck;

    public int coupons_id;
    public int my_coupons_id;
    public String coupons_no;
    public int tip;
    public int category;
    public int merchant_id;
    //0：通用   1：品牌   2：一级分类  3：二级分类  4：商家券
    public int coupons_type;
    public int coupons_type_id;
    public String coupons_name;
    public double meet_money;
    public double sale_money;
    public int total;
    public int receive_num;
    public int valid;
    //状态 0：正常 1：下架  2:已领取  3：已领完
    public int status;
    public int overdue;
    public String createtime;
    public String validtime;

    public String getType() {
        String type = "";
        switch (coupons_type) {
            case 0:
                type = "全部商品可用";
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                type = "部分商品可用";
                break;
        }
        return type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //显示的地方调用
    public String getMeet_money() {
        return ArithmeticUtil.convert(meet_money);
    }
    //显示的地方调用
    public String getSale_money() {
        return ArithmeticUtil.convert(sale_money);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeInt(this.coupons_id);
        dest.writeInt(this.my_coupons_id);
        dest.writeString(this.coupons_no);
        dest.writeInt(this.tip);
        dest.writeInt(this.category);
        dest.writeInt(this.merchant_id);
        dest.writeInt(this.coupons_type);
        dest.writeInt(this.coupons_type_id);
        dest.writeString(this.coupons_name);
        dest.writeDouble(this.meet_money);
        dest.writeDouble(this.sale_money);
        dest.writeInt(this.total);
        dest.writeInt(this.receive_num);
        dest.writeInt(this.valid);
        dest.writeInt(this.status);
        dest.writeInt(this.overdue);
        dest.writeString(this.createtime);
        dest.writeString(this.validtime);
    }

    public CouponsBean() {
    }

    protected CouponsBean(Parcel in) {
        this.isCheck = in.readByte() != 0;
        this.coupons_id = in.readInt();
        this.my_coupons_id = in.readInt();
        this.coupons_no = in.readString();
        this.tip = in.readInt();
        this.category = in.readInt();
        this.merchant_id = in.readInt();
        this.coupons_type = in.readInt();
        this.coupons_type_id = in.readInt();
        this.coupons_name = in.readString();
        this.meet_money = in.readDouble();
        this.sale_money = in.readDouble();
        this.total = in.readInt();
        this.receive_num = in.readInt();
        this.valid = in.readInt();
        this.status = in.readInt();
        this.overdue = in.readInt();
        this.createtime = in.readString();
        this.validtime = in.readString();
    }

    public static final Parcelable.Creator<CouponsBean> CREATOR = new Parcelable.Creator<CouponsBean>() {
        @Override
        public CouponsBean createFromParcel(Parcel source) {
            return new CouponsBean(source);
        }

        @Override
        public CouponsBean[] newArray(int size) {
            return new CouponsBean[size];
        }
    };
}
