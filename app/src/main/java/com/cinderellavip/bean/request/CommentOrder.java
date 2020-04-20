package com.cinderellavip.bean.request;

public class CommentOrder {

    public String choose_product_id;
    public String score;
    public String content;
    public String pics;

    public CommentOrder(String choose_product_id, String score, String content, String pics) {
        this.choose_product_id = choose_product_id;
        this.score = score;
        this.content = content;
        this.pics = pics;
    }
}
