package com.cinderellavip.bean.local;

import com.cinderellavip.util.ArithmeticUtil;

import java.util.List;

public class CartItem {








    public boolean isCheck;

    public List<CartGoodsItem> list;

    public CartItem(boolean isCheck, List<CartGoodsItem> list) {
        this.isCheck = isCheck;
        this.list = list;
    }


}
