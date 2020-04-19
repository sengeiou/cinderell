package com.cinderellavip.bean.local;

public class OrderBean {
    //  ALL = 0,UNPAY = 1,UNSEND = 2,UNRECEIVE = 3,FINISH = 4;
    //订单的状态
    public int type;
    //订单的详细状态

    public String status;

    public OrderBean(String status) {
        this.status = status;
    }

    public OrderBean(int type) {
        this.type = type;
    }


}
