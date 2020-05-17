package com.cinderellavip.bean.net.goods;

import com.cinderellavip.bean.net.SpecialItem;
import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class GroupInfo {
   public double group_price;
   public double product_price;
   public int group_user;
   public int has_user;
   public int end_time;
   public int timestamp;
    public List<SpecialItem> group_norms;

    public String getGroup_price() {
        return ArithmeticUtil.convert(group_price);
    }

    public String getProduct_price() {
        return ArithmeticUtil.convert(product_price);
    }
}
