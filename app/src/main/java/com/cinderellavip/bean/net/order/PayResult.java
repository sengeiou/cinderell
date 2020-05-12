package com.cinderellavip.bean.net.order;

import com.google.gson.annotations.SerializedName;

public class PayResult {




    public String pay_string;
    public payInfo pay_info;

    public class payInfo{

        public String appid;
        public String noncestr;
        @SerializedName("package")
        public String pack_age;
        public String partnerid;
        public String prepayid;
        public String timestamp;
        public String sign;
    }

}
