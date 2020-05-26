package com.cinderellavip.bean.request;

public class OrderRemark {
    public String store_id;
    public String remark = "";

    public OrderRemark(String store_id, String remark) {
        this.store_id = store_id;
        this.remark = remark;
    }
}
