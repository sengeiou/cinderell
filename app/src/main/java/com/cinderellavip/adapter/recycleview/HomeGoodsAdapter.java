package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;

import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class HomeGoodsAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public HomeGoodsAdapter() {
        super(R.layout.item_home_goods, null);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, @NotNull  String s) {
        int position = baseViewHolder.getLayoutPosition();
    }
}
