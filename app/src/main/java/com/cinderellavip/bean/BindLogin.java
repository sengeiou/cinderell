package com.cinderellavip.bean;


import java.io.Serializable;

public class BindLogin implements Serializable {

    public String unionid;
    public String nickname;
    public String avatar;
    public String sex;

    @Override
    public String toString() {
        return "BindLogin{" +
                "unionid='" + unionid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }



}
