package com.cinderellavip.bean.net.order;

import com.google.gson.annotations.SerializedName;

public class PayInfo {
    public String appid;
    public String noncestr;
    @SerializedName("package")
    public String pack_age;
    public String partnerid;
    public String prepayid;
    public String timestamp;
    public String sign;
}
