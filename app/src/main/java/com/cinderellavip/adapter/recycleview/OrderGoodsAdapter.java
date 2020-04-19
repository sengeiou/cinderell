package com.cinderellavip.adapter.recycleview;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class OrderGoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OrderGoodsAdapter() {
        super(R.layout.item_order_goods, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();

    }


}
