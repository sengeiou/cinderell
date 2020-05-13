package com.cinderellavip.bean.net.cart;

import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.http.ListResult;

import java.util.List;

public class CartResult extends ListResult<CartItem> {
    public List<HomeGoods> products;
}
