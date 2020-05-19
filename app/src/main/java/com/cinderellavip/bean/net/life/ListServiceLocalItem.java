package com.cinderellavip.bean.net.life;


import java.util.List;

/**
 * 本地的 一级列表页数据
 *
 * 为了把服务项目和 服务套餐做到 recycleview中
 */
public class ListServiceLocalItem {



    public List<LiftHomeServiceItem> data;


    public String title;

    public ListServiceLocalItem(List<LiftHomeServiceItem> data, String title) {
        this.data = data;
        this.title = title;
    }
}
