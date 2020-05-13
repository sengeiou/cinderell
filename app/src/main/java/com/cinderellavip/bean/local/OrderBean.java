package com.cinderellavip.bean.local;

import com.cinderellavip.bean.net.order.OrderGoodsInfo;

import java.util.List;

public class OrderBean {
    /**
     *  取消 没有按钮
     *
     *  待付款  取消 付款
     *
     *  待发货 取消
     *
     * 待收货 收货
     *
     *  已完成（待评价）  退款  评价
     *
     *  已完成（已评价）  退款
     */


    public int id;
    public String order_no;
    public String goods_amount;
    public int status;
    public String status_txt;
    public String create_at;
    public String send_sn;
    public String send_company;
    public int store_id;
    public String store_name;

    public List<OrderGoodsInfo> goods;

    public String getStatus() {
        //订单状态（0已取消，1待付款，2待发货，3待收货，4待评价[可退款]，5已完成[可退款]）
        String s = "";
        switch (status){
            case 0:
                s = "已取消";
                break;
            case 1:
                s = "待付款";
                break;
            case 2:
                s = "待发货";
                break;
            case 3:
                s = "待收货";
                break;
            case 4:
                s = "待评价";
                break;
            case 5:
                s = "已完成";
                break;
        }
        return s;
    }
}
