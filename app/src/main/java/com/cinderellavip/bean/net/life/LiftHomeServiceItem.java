package com.cinderellavip.bean.net.life;

import com.google.gson.annotations.SerializedName;

/**
 * 服务项目
 *
 */
public class LiftHomeServiceItem {
    public int id;
    public String title;
    public String thumb_nail;
    @SerializedName("picer")
    public String price;
    public String unit_name;
    public String city;

    public String duration;
    public String contain;
    public int typesof;
}
