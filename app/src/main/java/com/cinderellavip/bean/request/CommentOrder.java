package com.cinderellavip.bean.request;

public class CommentOrder {

    public String product_id;
    public String product_star;
    public String content;
    public String images;

    public CommentOrder(String product_id, String product_star, String content, String images) {
        this.product_id = product_id;
        this.product_star = product_star;
        this.content = content;
        this.images = images;
    }
}
