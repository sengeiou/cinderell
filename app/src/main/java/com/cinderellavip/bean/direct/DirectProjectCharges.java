package com.cinderellavip.bean.direct;

import com.cinderellavip.util.ArithmeticUtil;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DirectProjectCharges {

    public String title;

    public double price;


    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }
}
