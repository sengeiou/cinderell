package com.cinderellavip.bean.net.order;

import android.text.TextUtils;
import android.view.View;

import com.cinderellavip.bean.net.NetCityBean;

import java.util.ArrayList;
import java.util.List;

public class OrderInfo {




    public List<OrderGoodsInfo> goods;
    public NetCityBean address;


  

    public int id;
    public String order_no;
    public String goods_amount;
    public String ship_amount;
    public String dis_amount;
    public String score_amount;
    public String total_amount;
    public String payment;
    public int status;
    public String status_txt;
    public String pay_sn;
    public String pay_at;
    public String create_at;

    public String send_at;
    public String send_sn;
    public String send_company;
    public String receipt_at;
    public String commit_at;
    public int store_id;
    public String store_name;
    //拼团参数
    public long end_time;
    public long timestamp;
    public String  group_access;
    public int  total_user;
    public List<String> group_users;

    public boolean virtual;//是否是虚拟订单
    public String send_remark;//是否是虚拟订单

    public int invoice_status;//开票状态：-1不允许开票，0未申请开票，1已申请开票，2开票已完成
    public int invoice_state;//允许开票类型：1只支持电子发票，2只支持纸质发票，3两种都支持


    //是否可以开发票
    public int isInvoiceVisible(){
        if (invoice_status == -1 || TextUtils.isEmpty(invoiceString())){
            return View.GONE;
        }
        return View.VISIBLE;
    }


    //是否可以开发票
    public String invoiceString(){
        if (invoice_status == 0){
            return "申请开票";
        }else if (invoice_status == 1){
            return "已申请开票";
        }else if (invoice_status == 2){
            return "开票已完成";
        }
        return "";
    }

    public List<String> invoiceType(){
        List<String> list = new ArrayList<>();
        if (invoice_state == 1){
            list.add("电子普通发票");
        }else if (invoice_status == 2){
            list.add("纸质普通发票");
        }else if (invoice_status == 3){
            list.add("电子普通发票");
            list.add("纸质普通发票");
        }
        return list;
    }


}
