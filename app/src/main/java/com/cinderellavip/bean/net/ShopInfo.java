package com.cinderellavip.bean.net;

public class ShopInfo {
    public int id;
    public String image;
    public String name;
    public boolean collect;

    @Override
    public String toString() {
        return "ShopInfo{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", collect=" + collect +
                '}';
    }
}
