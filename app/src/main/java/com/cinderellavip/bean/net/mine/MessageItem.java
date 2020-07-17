package com.cinderellavip.bean.net.mine;

public class MessageItem {


    public int num;
    public int type;
    public String message;
    public String time;
    //详情需要的数据
    public String title;
    public String content;
    //只有订单消息才有
    public String status;
    public String image;

    //发现有
    public String obj_id;
    public int sub_type;


    public String getType() {
        if (type == 1){
            return "我的资产";
        }else if (type == 2){
            return "订单交易";
        }else if (type == 3){
            return "发现";
        }else if (type == 4){
            return "客服消息";
        }
        return "";
    }
}
