package com.cinderellavip.bean.local;

import com.cinderellavip.util.ArithmeticUtil;

public class CartItem {








    public boolean isCheck;
    /**
     * cart_id : 15
     * product_id : 27
     * sku_id : 17
     * other_id : 0
     * logo : sku/20191009/9edfbf530d560ccf5afc3c43d8bbfa04.jpg
     * product_name : 越南红心火龙果5个410g以上/个(大果)
     * norm : 1kg
     * other_name :
     * price : 7.5
     * num : 1
     */

    private int cart_id;
    private int product_id;
    private int sku_id;
    private int other_id;
    private int area_id;
    private String logo;
    private String product_name;
    private String norm;
    private String other_name;
    private double price;
    private int num;

    public boolean isSpecialGoods(){
        return 4 == area_id;
    }

    public CartItem(boolean isCheck) {
        this.isCheck = isCheck;
    }


    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getSku_id() {
        return sku_id;
    }

    public void setSku_id(int sku_id) {
        this.sku_id = sku_id;
    }

    public int getOther_id() {
        return other_id;
    }

    public void setOther_id(int other_id) {
        this.other_id = other_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public double getPrice() {
        return price;
    }

    public String getShowPrice() {
        return ArithmeticUtil.convert(price);
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


}
