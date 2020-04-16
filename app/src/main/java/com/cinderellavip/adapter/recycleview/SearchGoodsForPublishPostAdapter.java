package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;


public class SearchGoodsForPublishPostAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SearchGoodsForPublishPostAdapter() {
        super(R.layout.item_search_goods_for_publishpost, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  String item) {
        int position = helper.getAdapterPosition();



    }



}
