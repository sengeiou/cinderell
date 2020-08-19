package com.cinderellavip.adapter.recycleview;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cinderellavip.R;

public class SearchShopAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public SearchShopAdapter() {
        super(R.layout.item_search_shop, null);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        int position = helper.getAdapterPosition();
//
    }

}
