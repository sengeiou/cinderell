package com.cinderellavip.bean.net.home;

import com.cinderellavip.util.ArithmeticUtil;

public class HomeSpikeItem {



    public int id;
    public int spike_id;
    public String name;
    public String thumb;
    public double buy_price;
    public double spike_price;
    public int spike_num;
    public String spike_rate;
    public double rate;
    public boolean is_sold_out;
    public int status;

    public String getBuyPrice() {
        return ArithmeticUtil.convert(buy_price);
    }

    public String getSpikePrice() {
        return ArithmeticUtil.convert(spike_price);
    }
}
