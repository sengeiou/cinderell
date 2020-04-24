package com.cinderellavip.bean.local;

public class TimeBean {




    public int type;
    public String time;

    public TimeBean(int type, String time) {
        this.type = type;
        this.time = time;
    }

    public TimeBean(int status) {
        this.type = status;
    }



}
