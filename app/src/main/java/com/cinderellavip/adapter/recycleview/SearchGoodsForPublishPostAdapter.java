package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.global.ImageUtil;


public class SearchGoodsForPublishPostAdapter extends BaseQuickAdapter<HomeGoods, BaseViewHolder> {

    public SearchGoodsForPublishPostAdapter() {
        super(R.layout.item_search_goods_for_publishpost, null);
    }


    @Override
    protected void convert( BaseViewHolder helper,  HomeGoods item) {
        int position = helper.getAdapterPosition();
//        TextView tv_tip = helper.getView(R.id.tv_tip);
//        tv_tip.setVisibility(position == 0? View.VISIBLE:View.GONE);
        ImageView iv_image = helper.getView(R.id.iv_image);
        ImageUtil.loadNet(getContext(),iv_image,item.product_thumb);
        helper.setText(R.id.tv_name,item.product_name)
                .setText(R.id.tv_price,"￥"+item.getProduct_price());



    }



}
