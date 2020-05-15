package com.cinderellavip.bean.net.order;

import java.util.List;

public class ReturnOrderInfoResult {

    public OrderGoodsInfo goods;

    public int status;
    public String status_txt;
    public String order_no;
    public String total_amount;
    public String create_at;
    public String pay_at;
    public String send_at;
    public String receipt_at;
    public String refund_reason;
    public String refund_submit_at;
    public String refund_comment;
    public List<String> refund_images;
    public String refund_review_at;
    public String fail_reason;

}
