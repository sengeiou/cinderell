package com.cinderellavip.bean.net.find;


/**
 * Created by jumpbox on 16/8/23.
 */
public class FindItem{
    public int id;
    public int type;
    public int see_num;

    public String title;
    public String thumb;
    public String topic;
    public String content;
    public String user_avatar;
    public String user_name;


    //是否是话题
    public boolean isTopic(){
        return type == 2;
    }


    //为了收藏
    public String store_name;
    public String store_id;
    public String brand_id;
    public String brand_name;
    public String brand_logo;



}
