package com.cinderellavip.bean.net.life;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResult {


    public CategoryService service;

    public List<LiftHomeServiceItem> project;
    @SerializedName("package")
    public List<LiftHomeServiceItem> pack_age;

    @SerializedName("long")
    public LiftHomeServiceItem lo_ng;

    public NewPersonCoupon coupon;




}
