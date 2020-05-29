package com.cinderellavip.bean.direct;

import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class DirectProjectInfo {
    public int id;

    public String title;
    public String thumb_nail;
    public String duration;
    public String attitude;
    public String efficacy;
    public String unsuitable;
    public String process;
    public String supplies;
    public String covenant;

    public double price;
    public String city;
    public int typesof;

    public List<DirectProjectCharges> Charges;
//    public DirectPersonComment comment;

    public String getPrice() {
        return ArithmeticUtil.convert(price);
    }
}
