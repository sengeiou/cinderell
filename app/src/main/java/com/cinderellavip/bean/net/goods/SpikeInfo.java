package com.cinderellavip.bean.net.goods;

import com.cinderellavip.bean.net.SpecialItem;
import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class SpikeInfo {
   public double spike_price;
   public double product_price;
   public int spike_num;
   public int end_time;
   public int timestamp;
    public List<SpecialItem> spike_norms;

    public String getSpikePrice() {
        return ArithmeticUtil.convert(spike_price);
    }

    public String getProductPrice() {
        return ArithmeticUtil.convert(product_price);
    }
}
