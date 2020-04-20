package com.cinderellavip.bean;

/**
 * 订单列表
 */
public class OrderBean {


    public int child_order_id;
    public int refund_id;
    public int order_id;
    public String child_order_no;
    // 0：自提  1：物流
    public int delivery_type;
    //0：正常 1：已取消 2：待付款  3:备货中 4：待发货 5：待收货 6：待评价 7:已完成8:退款中 9:退款完成
    public int status;
    public int product_num;
    public String logistics_no;
    public String createtime;
    public String paytime;
    public double pay_fee;






}
