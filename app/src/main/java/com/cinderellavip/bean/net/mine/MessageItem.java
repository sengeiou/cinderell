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

    public String getType() {
        if (type == 1){
            return "我的资产";
        }if (type == 2){
            return "订单交易";
        }if (type == 3){
            return "发现";
        }
        return "";
    }
}
