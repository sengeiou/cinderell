package com.cinderellavip.adapter.recycleview;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;
import com.cinderellavip.weight.TopRoundImageSquareView;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class LikeGoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public LikeGoodsAdapter() {
        super(R.layout.item_home_goods, null);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NotNull  String homeGoods) {
        int position = baseViewHolder.getLayoutPosition();
        TopRoundImageSquareView iv_image =  baseViewHolder.getView(R.id.iv_image);
        iv_image.setImageResource(R.mipmap.demo_like_product);
       TextView tv_name =  baseViewHolder.getView(R.id.tv_name);
       TextView tv_group_number =  baseViewHolder.getView(R.id.tv_group_number);
       TextView tv_price =  baseViewHolder.getView(R.id.tv_price);
       TextView tv_former_price =  baseViewHolder.getView(R.id.tv_former_price);
       ImageView iv_national_flag =  baseViewHolder.getView(R.id.iv_national_flag);

    }
}
