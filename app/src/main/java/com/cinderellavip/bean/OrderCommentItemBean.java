package com.cinderellavip.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentItemBean implements Parcelable {

    public String product_id;
    public String child_order_id;
    public String logo;
    public String name;
    public String norm;

    //得到上传的数据
    public String score;
    public String content;

    //图片和视频源数据
    public List<OrderCommentImageItemBean> mDatas  = new ArrayList<>();

    public String getPics(){
        String pics = "";
        for (OrderCommentImageItemBean bean:mDatas){
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
        dest.writeString(this.product_id);
        dest.writeString(this.child_order_id);
        dest.writeString(this.logo);
        dest.writeString(this.name);
        dest.writeString(this.norm);
        dest.writeString(this.score);
        dest.writeString(this.content);
        dest.writeList(this.mDatas);
    }

    public OrderCommentItemBean() {
    }

    protected OrderCommentItemBean(Parcel in) {
        this.product_id = in.readString();
        this.child_order_id = in.readString();
        this.logo = in.readString();
        this.name = in.readString();
        this.norm = in.readString();
        this.score = in.readString();
        this.content = in.readString();
        this.mDatas = new ArrayList<OrderCommentImageItemBean>();
        in.readList(this.mDatas, OrderCommentImageItemBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<OrderCommentItemBean> CREATOR = new Parcelable.Creator<OrderCommentItemBean>() {
        @Override
        public OrderCommentItemBean createFromParcel(Parcel source) {
            return new OrderCommentItemBean(source);
        }

        @Override
        public OrderCommentItemBean[] newArray(int size) {
            return new OrderCommentItemBean[size];
        }
    };
}
