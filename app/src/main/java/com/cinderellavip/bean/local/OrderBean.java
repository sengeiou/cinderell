package com.cinderellavip.bean.local;

public class OrderBean {
    //  ALL = 0,UNPAY = 1,UNSEND = 2,UNRECEIVE = 3,FINISH = 4;


    public int type;

    public OrderBean(int status) {
        this.type = status;
    }



}
