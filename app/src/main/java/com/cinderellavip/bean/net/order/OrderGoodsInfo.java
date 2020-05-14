package com.cinderellavip.bean.net.order;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.cinderellavip.bean.OrderCommentImageItemBean;

import java.util.ArrayList;
import java.util.List;

public class OrderGoodsInfo implements Parcelable {





    public int product_id;
    public String product_thumb;
    public String product_name;
    public String product_norm;
    public int product_norm_id;
    public String product_price;
    public int product_num;

    public String dis_price;

    //订单详情里
    public int refund_num;

    //评价使用的
    //得到上传的数据
    public String product_star;
    public String content;

    //图片和视频源数据
    public List<OrderCommentImageItemBean> images  = new ArrayList<>();

    public String getPics(){
        String pics = "";
        for (OrderCommentImageItemBean bean:images){
            if (!TextUtils.isEmpty(bean.netPath)){
                pics += bean.netPath+";";
            }
        }
        if (pics.endsWith(";")){
            pics = pics.substring(0,pics.length()-1);
        }
        return pics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.product_id);
        dest.writeString(this.product_thumb);
        dest.writeString(this.product_name);
        dest.writeString(this.product_norm);
        dest.writeInt(this.product_norm_id);
        dest.writeString(this.product_price);
        dest.writeInt(this.product_num);
        dest.writeString(this.dis_price);
        dest.writeInt(this.refund_num);
        dest.writeString(this.product_star);
        dest.writeString(this.content);
        dest.writeTypedList(this.images);
    }

    public OrderGoodsInfo() {
    }

    protected OrderGoodsInfo(Parcel in) {
        this.product_id = in.readInt();
        this.product_thumb = in.readString();
        this.product_name = in.readString();
        this.product_norm = in.readString();
        this.product_norm_id = in.readInt();
        this.product_price = in.readString();
        this.product_num = in.readInt();
        this.dis_price = in.readString();
        this.refund_num = in.readInt();
        this.product_star = in.readString();
        this.content = in.readString();
        this.images = in.createTypedArrayList(OrderCommentImageItemBean.CREATOR);
    }

    public static final Parcelable.Creator<OrderGoodsInfo> CREATOR = new Parcelable.Creator<OrderGoodsInfo>() {
        @Override
        public OrderGoodsInfo createFromParcel(Parcel source) {
            return new OrderGoodsInfo(source);
        }

        @Override
        public OrderGoodsInfo[] newArray(int size) {
            return new OrderGoodsInfo[size];
        }
    };
}
