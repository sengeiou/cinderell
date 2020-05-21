package com.cinderellavip.adapter.recycleview;


import android.app.Activity;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.bean.local.HomeGoods;
import com.cinderellavip.global.ImageUtil;
import com.cinderellavip.ui.activity.home.GoodsDetailActivity;
import com.cinderellavip.ui.activity.home.ShopDetailActivity;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class SearchGoodsAdapter extends BaseQuickAdapter<HomeGoods, BaseViewHolder> implements LoadMoreModule {

    public SearchGoodsAdapter() {
        super(R.layout.item_search_goods, null);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NotNull  HomeGoods homeGoods) {
        int position = baseViewHolder.getLayoutPosition();
       TextView tv_name =  baseViewHolder.getView(R.id.tv_name);
       TextView tv_price =  baseViewHolder.getView(R.id.tv_price);
       TextView tv_former_price =  baseViewHolder.getView(R.id.tv_former_price);
       TextView tv_shop_name =  baseViewHolder.getView(R.id.tv_shop_name);
        ImageView iv_image =  baseViewHolder.getView(R.id.iv_image);
        tv_name.setText(homeGoods.name);
        ImageUtil.loadNet2(getContext(),iv_image,homeGoods.thumb);

        tv_price.setText("￥"+homeGoods.getPrice());
        tv_former_price.setText("￥"+homeGoods.getOld_price());
        tv_shop_name.setText(homeGoods.store_name);


           tv_name.setMaxLines(2);
           tv_name.setLines(2);
           tv_former_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

        baseViewHolder.getView(R.id.ll_root).setOnClickListener(v -> {
            GoodsDetailActivity.launch((Activity) getContext(),homeGoods.id+"");
        });
        baseViewHolder.getView(R.id.tv_go_shop).setOnClickListener(v -> {
            ShopDetailActivity.launchShop(getContext(),homeGoods.store_id+"");
        });
    }
}
