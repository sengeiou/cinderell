package com.cinderellavip.adapter.recycleview;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.net.HomeCategoryItem;
import com.cinderellavip.ui.activity.home.ShopGoodsListActivity;


public class MerchantGoodsCategoryAdapter extends BaseQuickAdapter<HomeCategoryItem, BaseViewHolder> {


    int  store_id;
    public MerchantGoodsCategoryAdapter(int store_id) {
        super(R.layout.item_merchant_goods_category, null);
        this.store_id = store_id;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final HomeCategoryItem item) {
        int position = helper.getAdapterPosition();
        helper.setText(R.id.tv_title,item.name);

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            ShopGoodsListActivity.launch((Activity) getContext(),item.name,item.id+"",""+store_id);
        });


//        TextView tv_price = helper.getView(R.id.tv_price);
//        TextView tv_former_price = helper.getView(R.id.tv_former_price);
//
//        tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
//        ImageUtil.loadNet(mContext,iv_image,item.logo);
//        tv_title.setText(item.product_name);
//        tv_price.setText("￥"+item.price);
//        tv_former_price.setText("￥"+item.price);
//


    }

    /**
     * 动态添加布局
     * @param str
     */
    private void addTextView(String str) {

    }







}
